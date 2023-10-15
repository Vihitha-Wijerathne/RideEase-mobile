package com.example.rideease.DayPass

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.rideease.Modals.RouteModel
import com.example.rideease.R
import com.google.firebase.database.*

class DaypassSelect : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var routeIdTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daypass_select)
        database = FirebaseDatabase.getInstance().getReference("Routes").child("177")
        routeIdTextView = findViewById(R.id.routeIdTextView)

        // Call the function to retrieve data
        getRouteData()
    }

    private fun getRouteData() {
        // Read data from the specific route ID "177"
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val route = snapshot.getValue(RouteModel::class.java)
                route?.let {
                    // Display the route ID in the TextView
                    routeIdTextView.text = "Route ID: ${it.routeId}"
                } ?: run {
                    // Handle the case where the route data with ID "177" does not exist
                    routeIdTextView.text = "Route data not found"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database read error
                routeIdTextView.text = "Error fetching route data"
            }
        })
    }

    // Handle the "Buy Day Pass" button click
    fun onBuyDayPassClick(view: View) {
        // Implement your logic when the "Buy Day Pass" button is clicked
    }
}
