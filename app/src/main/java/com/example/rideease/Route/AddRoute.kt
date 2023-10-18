package com.example.rideease.Route

import RouteModel
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.rideease.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddRoute : AppCompatActivity() {

    private lateinit var editTextRouteSource: EditText
    private lateinit var editTextRouteDestination: EditText
    private lateinit var editTextRouteId: EditText
    private lateinit var editTextFare: EditText
    private lateinit var buttonAddRoute: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_route)

        editTextRouteSource = findViewById(R.id.editTextRouteSource)
        editTextRouteDestination = findViewById(R.id.editTextRouteDestination)
        editTextRouteId = findViewById(R.id.editTextRouteId)
        editTextFare = findViewById(R.id.editTextFare)
        buttonAddRoute = findViewById(R.id.buttonAddRoute)

        database = FirebaseDatabase.getInstance().getReference("Routes")

        buttonAddRoute.setOnClickListener {
            addRoute()
        }
    }

    private fun addRoute() {
        val routeSource = editTextRouteSource.text.toString().trim()
        val routeDestination = editTextRouteDestination.text.toString().trim()
        val routeId = editTextRouteId.text.toString().trim()
        val fare = editTextFare.text.toString().trim()

        if (routeSource.isNotEmpty() && routeDestination.isNotEmpty() && routeId.isNotEmpty() && fare.isNotEmpty()) {
            val routeIdInt = routeId.toInt()
            val fareDouble = fare.toDouble()

            val route = RouteModel(routeSource, routeDestination, routeId, fareDouble)
            val routeIdString = routeIdInt.toString()

            database.child(routeIdString).setValue(route).addOnSuccessListener {
                // Route added successfully
                finish() // Close the activity after adding the route
            }
        } else {
            // Show an error message if any field is empty
            // You can display a Toast or any other error handling mechanism here
        }
    }
}
