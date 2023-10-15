package com.example.rideease

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rideease.Adapter.JourneyHistoryAdapter
import com.example.rideease.Modals.JourneyHistoryModal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.R

class JourneyHistory : AppCompatActivity() {

    private lateinit var cbresult: ArrayList<JourneyHistoryModal>
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recordRecyclerView: RecyclerView
    private lateinit var recordAdapter: JourneyHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.rideease.R.layout.activity_journey_history)


        cbresult = ArrayList()
        recordRecyclerView = findViewById(com.example.rideease.R.id.recordRecyclerView)
        recordRecyclerView.layoutManager = LinearLayoutManager(this)

        recordAdapter = JourneyHistoryAdapter(cbresult) { testResultToDelete ->
            // Handle the deletion of the item (e.g., remove it from the list)
            cbresult.remove(testResultToDelete)
            recordAdapter.notifyDataSetChanged()

            // You can also delete the data from your database here
            // For example, using Firebase Realtime Database:
            // dbRef.child(testResultToDelete.id).removeValue()
        }
        recordRecyclerView.adapter = recordAdapter

        getresults()
    }

    private fun getresults() {
        var userId = ""
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser?.let {
            userId = it.uid
        }
        dbRef = FirebaseDatabase.getInstance().getReference("JourneyHistory")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cbresult.clear()
                if (snapshot.exists()) {
                    for (cbResultsSnap in snapshot.children) {
                        val cbResults = cbResultsSnap.getValue(JourneyHistoryModal::class.java)
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