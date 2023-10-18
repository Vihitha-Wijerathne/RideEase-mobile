package com.example.rideease

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rideease.Adapter.LoanAdapter
import com.example.rideease.Modals.LoanModal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.android.material.bottomsheet.BottomSheetDialog

class LoanHistory : AppCompatActivity() {
    private lateinit var loanResult: ArrayList<LoanModal>
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var loanRecyclerView: RecyclerView
    private lateinit var loanAdapter: LoanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_history)

        loanResult = ArrayList()
        loanAdapter = LoanAdapter(loanResult, this::deleteLoan)

        // Initialize the RecyclerView
        loanRecyclerView = findViewById(R.id.loanRecyclerView)
        loanRecyclerView.layoutManager = LinearLayoutManager(this)
        loanRecyclerView.adapter = loanAdapter

        getResults()
    }

    private fun getResults() {
        var userId = ""
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser?.let {
            userId = it.uid
        }
        dbRef = FirebaseDatabase.getInstance().getReference("loan")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                loanResult.clear()
                if (snapshot.exists()) {
                    for (loanResultsSnap in snapshot.children) {
                        val loanResults = loanResultsSnap.getValue(LoanModal::class.java)
                        if (loanResults != null && loanResults.userId == userId) {
                            loanResult.add(loanResults)
                        }
                    }
                    loanAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error here (e.g., log or display an error message)
            }
        })
    }

    // Function to delete a loan
    private fun deleteLoan(loan: LoanModal) {
        val loanId = loan.loanId
        val dbRef = loanId?.let { FirebaseDatabase.getInstance().getReference("loan").child(it) }

        if (dbRef != null) {
            dbRef.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Deletion was successful in the database
                    // Now, remove the item from the local list
                    val removedIndex = loanResult.indexOf(loan)
                    if (removedIndex != -1) {
                        loanResult.removeAt(removedIndex)
                        loanAdapter.notifyDataSetChanged()
                    }
                } else {
                    // Handle the database deletion error
                    // You can log or display an error message here
                }
            }
        }
    }
}
