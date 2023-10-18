package com.example.rideease.Recharge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.example.rideease.Modals.RechargeModal
import com.example.rideease.R
import com.example.rideease.Adapter.RechargeHistoryAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RechargeHistory : AppCompatActivity() {
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var rechargeHistoryAdapter: RechargeHistoryAdapter
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userId: String = firebaseAuth.currentUser?.uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge_history)

        historyRecyclerView = findViewById(R.id.historyRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        historyRecyclerView.layoutManager = layoutManager

        // Get a reference to the Firebase Database for the user's recharge history
        val historyDatabase = FirebaseDatabase.getInstance().getReference("recharge_history").child(userId)

        // Retrieve the recharge history list from the database
        historyDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val rechargeHistoryList = ArrayList<RechargeModal>()

                for (historySnapshot in dataSnapshot.children) {
                    val historyItem = historySnapshot.getValue(RechargeModal::class.java)
                    if (historyItem != null) {
                        rechargeHistoryList.add(historyItem)
                    }
                }

                if (rechargeHistoryList.isNotEmpty()) {
                    // If the list is not empty, create and set the adapter
                    rechargeHistoryAdapter = RechargeHistoryAdapter(rechargeHistoryList)
                    historyRecyclerView.adapter = rechargeHistoryAdapter
                } else {
                    // Handle the case where the list is empty, e.g., display a message
                    Toast.makeText(this@RechargeHistory, "Recharge history is empty", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors or exceptions when retrieving data from the database
                Toast.makeText(this@RechargeHistory, "An error occurred while retrieving recharge history", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
