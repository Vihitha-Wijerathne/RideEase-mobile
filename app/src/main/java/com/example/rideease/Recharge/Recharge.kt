import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rideease.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Recharge : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private val userId = "your_user_id" // Replace with the actual user ID
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge)

        firebaseAuth = FirebaseAuth.getInstance()

        val userAccountRef = database.getReference("users/$userId") // Replace with the actual path in your database

        userAccountRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userAccount = dataSnapshot.getValue(UserAccount::class.java)
                    if (userAccount != null) {
                        textViewUserName.text = userAccount.userName
                        textViewAvailableBalanceValue.text = "$" + userAccount.availableBalance.toString()
                        textViewLoanBalanceValue.text = "$" + userAccount.loanBalance.toString()
                    }
                }
            }

//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle errors if needed
//            }
        })

        buttonRecharge.setOnClickListener {
            val rechargeAmount = editTextRechargeAmount.text.toString().toDouble()

            userAccountRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userAccount = dataSnapshot.getValue(UserAccount::class.java)
                        if (userAccount != null) {
                            // Deduct the recharge amount from the loan balance
                            userAccount.loanBalance -= rechargeAmount

                            // If there's an excess amount, add it to the available balance
                            if (rechargeAmount > userAccount.loanBalance) {
                                userAccount.availableBalance += (rechargeAmount - userAccount.loanBalance)
                                userAccount.loanBalance = 0.0
                            }

                            // Update the user account details in the database
                            userAccountRef.setValue(userAccount)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors if needed
                }
            })
        }
    }
}
