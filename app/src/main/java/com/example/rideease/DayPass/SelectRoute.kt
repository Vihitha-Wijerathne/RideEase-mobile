package com.example.rideease.DayPass

import RouteModel
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.rideease.Modals.UserModal
import com.example.rideease.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SelectRoute : AppCompatActivity() {

    private lateinit var routesContainer: LinearLayout
    private lateinit var database: DatabaseReference
    private lateinit var buttonBuyDaypass: Button
    private lateinit var routeData: RouteModel
    private lateinit var user: UserModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daypass_select)

        routesContainer = findViewById(R.id.routesContainer)
        buttonBuyDaypass = findViewById(R.id.buttonBuyDaypass)
        database = FirebaseDatabase.getInstance().getReference("Routes")
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(userId)
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        user = snapshot.getValue(UserModal::class.java)!!
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error if necessary
                }
            })
        }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (routeSnapshot in snapshot.children) {
                        val route = routeSnapshot.getValue(RouteModel::class.java)
                        if (route != null) {
                            val routeItemView = layoutInflater.inflate(R.layout.route_item, null)
                            val routeIdTextView: TextView = routeItemView.findViewById(R.id.routeIdTextView)
                            val sourceTextView: TextView = routeItemView.findViewById(R.id.sourceTextView)
                            val destinationTextView: TextView = routeItemView.findViewById(R.id.destinationTextView)
                            val fareTextView: TextView = routeItemView.findViewById(R.id.fareTextView)

                            routeItemView.setOnClickListener {
                                routeData = route
                            }

                            routeIdTextView.text = "Route ID: ${route.routeId}"
                            sourceTextView.text = "Source: ${route.source}"
                            destinationTextView.text = "Destination: ${route.destination}"
                            fareTextView.text = "Fare: ${route.fare}"

                            routesContainer.addView(routeItemView)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error if necessary
            }
        })

        buttonBuyDaypass.setOnClickListener {
            if (::routeData.isInitialized && ::user.isInitialized) {
                val selectedRouteFare = routeData.fare ?: 0.0
                val userBalance = user.balance ?: 0.0

                if (userBalance >= selectedRouteFare) {
                    // Deduct fare from user's balance
                    val newBalance = userBalance - selectedRouteFare
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    if (userId != null) {
                        FirebaseDatabase.getInstance().getReference("users").child(userId).child("balance").setValue(newBalance)
                        // Perform the necessary operations after successful purchase
                        // For example, navigate to a confirmation page or show a success message.
                    }
                } else {
                    // Insufficient balance, show an error message to the user.
                    // For example, display a Toast message: "Insufficient balance to purchase this route."
                }
            }
        }
    }
}
