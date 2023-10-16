import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rideease.R
import com.example.rideease.Modals.UserModal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DaypassSelect : AppCompatActivity() {

    private lateinit var routesContainer: LinearLayout
    private lateinit var database: DatabaseReference
    private lateinit var userRef: DatabaseReference
    private lateinit var userId: String
    private lateinit var userBalanceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daypass_select)

        routesContainer = findViewById(R.id.routesContainer)

        database = FirebaseDatabase.getInstance().getReference("Routes")
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        userRef = FirebaseDatabase.getInstance().getReference("users").child(userId)

//        userRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val user = snapshot.getValue(UserModal::class.java)
//                val userBalance = user?.balance ?: 0.0
//                userBalanceTextView.text = "Balance: $userBalance" // Update user's balance in the TextView
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle database error
//            }
//        })

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

                            routeIdTextView.text = "Route ID: ${route.routeId}"
                            sourceTextView.text = "Source: ${route.source}"
                            destinationTextView.text = "Destination: ${route.destination}"
                            fareTextView.text = "Fare: ${route.fare}"

                            routeItemView.setOnClickListener {
                                buyDaypass(route.fare ?: 0.0)
                            }

                            routesContainer.addView(routeItemView)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }

    private fun buyDaypass(routeFare: Double) {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModal::class.java)
                val currentBalance = user?.balance ?: 0.0
                if (currentBalance >= 100) {
                    val newBalance = currentBalance - 100
                    userRef.child("balance").setValue(newBalance)
                    showToast("You bought the Daypass!")
                    navigateToMainActivity()
                } else {
                    showToast("Insufficient balance to purchase Daypass.")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity() {
        // Implement logic to navigate to the main activity
    }
}
