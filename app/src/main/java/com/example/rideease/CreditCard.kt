package com.example.rideease

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rideease.Adapter.CreditCardAdapter
import com.example.rideease.Modals.AddCardModal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CreditCard : AppCompatActivity() {
    private lateinit var creditCards: ArrayList<AddCardModal>
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recordRecyclerView: RecyclerView
    private lateinit var recordAdapter: CreditCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_cards)

        creditCards = ArrayList()

        recordAdapter = CreditCardAdapter(creditCards, this::deleteItem)
        recordRecyclerView = findViewById(R.id.creditCardRecyclerView)
        recordRecyclerView.layoutManager = LinearLayoutManager(this)
        recordRecyclerView.adapter = recordAdapter

        getCreditCards()
    }

    private fun getCreditCards() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            dbRef = FirebaseDatabase.getInstance().getReference("CreditCards")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    creditCards.clear()
                    if (snapshot.exists()) {
                        for (cbResultsSnap in snapshot.children) {
                            val cbResults = cbResultsSnap.getValue(AddCardModal::class.java)
                            if (cbResults != null && cbResults.userId == userId) {
                                creditCards.add(cbResults)
                            }
                        }
                        recordAdapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the database error here
                    Log.e("DatabaseError", "Error fetching credit cards: ${error.message}")
                }
            })
        }
    }

    private fun deleteItem(card: AddCardModal) {
        val cardNumber = card.cardId
        if (cardNumber != null) {
            val dbRef = FirebaseDatabase.getInstance().getReference("CreditCards").child(cardNumber)
            dbRef.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Deletion was successful in the database
                    // Now, go back to the previous page
                    finish() // Close the current activity

                    // Optionally, start a new activity to re-enter the page
                    val intent = Intent(this, CreditCard::class.java)
                    startActivity(intent)
                } else {
                    // Handle the database deletion error
                    Log.e("DatabaseDeletionError", "Error deleting item: ${task.exception?.message}")
                }
            }
        }
    }

}
