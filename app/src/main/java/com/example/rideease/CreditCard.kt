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

        recordAdapter = CreditCardAdapter(cbresult) { testResultToDelete ->
            // Handle the deletion of the item (e.g., remove it from the list)
            cbresult.remove(testResultToDelete)
            recordAdapter.notifyDataSetChanged()
        }


        val openDialogButton = findViewById<Button>(R.id.openDialogButton)
        openDialogButton.setOnClickListener {
            // When the button is clicked, open the dialog
            openCardDetailsDialog()
        }

        getresults()
    }

    private fun openCardDetailsDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_credit_card_details, null)
        val dialogRecyclerView = dialogView.findViewById<RecyclerView>(R.id.dialogRecyclerView)

        // Set up the RecyclerView in the dialog with card details
        val dialogAdapter = CreditCardAdapter(cbresult) { testResultToDelete ->
            // Handle interactions within the dialog
        }
        dialogRecyclerView.layoutManager = LinearLayoutManager(this)
        dialogRecyclerView.adapter = dialogAdapter

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)
        dialog.show()
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
