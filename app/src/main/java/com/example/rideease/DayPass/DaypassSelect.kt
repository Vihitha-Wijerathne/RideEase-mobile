import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.rideease.R
import com.google.firebase.database.*

class DaypassSelect : AppCompatActivity() {

    private lateinit var routesContainer: LinearLayout
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daypass_select)

        routesContainer = findViewById(R.id.routesContainer)
        database = FirebaseDatabase.getInstance().getReference("Routes")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (routeSnapshot in snapshot.children) {
                        val route = routeSnapshot.getValue(RouteModel::class.java)
                        if (route != null) {
                            // Inflate the route item layout
                            val routeItemView = layoutInflater.inflate(R.layout.route_item, null)

                            // Find views in the route item layout
                            val routeIdTextView: TextView = routeItemView.findViewById(R.id.routeIdTextView)
                            val sourceTextView: TextView = routeItemView.findViewById(R.id.sourceTextView)
                            val destinationTextView: TextView = routeItemView.findViewById(R.id.destinationTextView)
                            val fareTextView: TextView = routeItemView.findViewById(R.id.fareTextView)

                            // Set data to the views
                            routeIdTextView.text = "Route ID: ${route.routeId}"
                            sourceTextView.text = "Source: ${route.source}"
                            destinationTextView.text = "Destination: ${route.destination}"
                            fareTextView.text = "Fare: ${route.fare}"

                            // Add the route item view to the container
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
}
