package com.example.rideease.Route

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rideease.Modals.RouteModel
import com.example.rideease.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Route : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        firebaseAuth = FirebaseAuth.getInstance()

        getRoutes { routes ->
            // Handle the retrieved routes here
            for (route in routes) {
                println("Route ID: ${route.routeId}, Fare: ${route.fare}")
            }
        }
    }

    private fun getRoutes(callback: (List<RouteModel>) -> Unit) {
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Routes")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val routesList = mutableListOf<RouteModel>()

                for (routeSnapshot in snapshot.children) {
                    val route = routeSnapshot.getValue(RouteModel::class.java)
                    route?.let {
                        routesList.add(it)
                    }
                }

                // Invoke the callback with the list of retrieved routes
                callback(routesList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Database", "Error: ${error.message}")
                Toast.makeText(this@Route, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                // Handle database read error
            }

        })
    }
}
