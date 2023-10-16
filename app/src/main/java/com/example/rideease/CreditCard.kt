package com.example.rideease

import android.widget.Button

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rideease.Adapter.CreditCardAdapter
import com.example.rideease.Modals.AddCardModal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.android.material.bottomsheet.BottomSheetDialog

class CreditCard : AppCompatActivity() {
    private lateinit var cbresult: ArrayList<AddCardModal>
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recordRecyclerView: RecyclerView
    private lateinit var recordAdapter: CreditCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_cards)

        cbresult = ArrayList()

        // Initialize the RecyclerView and its adapter
        recordRecyclerView = findViewById(R.id.creditCardRecyclerView)
        recordRecyclerView.layoutManager = LinearLayoutManager(this)
        recordAdapter = CreditCardAdapter(cbresult)
        recordRecyclerView.adapter = recordAdapter


        getresults()
    }

    private fun getresults() {
        var userId = ""
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser?.let {
            userId = it.uid
        }
        dbRef = FirebaseDatabase.getInstance().getReference("CreditCards")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cbresult.clear()
                if (snapshot.exists()) {
                    for (cbResultsSnap in snapshot.children) {
                        val cbResults = cbResultsSnap.getValue(AddCardModal::class.java)
                        if (cbResults?.userId == userId) {
                            cbresult.add(cbResults!!)
                        }
                    }
                    recordAdapter.notifyDataSetChanged() // Notify the adapter of data changes
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error here
            }
        })
    }
}

