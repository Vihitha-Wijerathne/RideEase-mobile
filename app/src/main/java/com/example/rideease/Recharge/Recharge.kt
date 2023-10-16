package com.example.rideease.Recharge

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.rideease.Modals.UserModal
import com.example.rideease.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Recharge : AppCompatActivity() {
    private lateinit var usernametxt: TextView
    private lateinit var avlbalancetxt: TextView
    private lateinit var loanbalancetxt: TextView
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userId : String// Replace with the actual user ID
    private lateinit var username: String
    private var cbalance: Double = 0.0
    private var uloandue: Double = 0.0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge)

        usernametxt = findViewById(R.id.textViewUserName)
        avlbalancetxt = findViewById(R.id.textViewAvailableBalanceValue)
        loanbalancetxt = findViewById(R.id.textViewLoanBalanceValue)

        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        user?.let {

            userId = it.uid
            database = FirebaseDatabase.getInstance().getReference("users").child(userId)

            database.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userAccount = dataSnapshot.getValue(UserModal::class.java)
                        username = userAccount?.name!!
                        cbalance = userAccount?.balance!!
                        uloandue = userAccount?.loandue!!
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Recharge,"There is a problem of retrieving data from database", Toast.LENGTH_LONG).show()
                }

            })
        }
//        buttonRecharge.setOnClickListener {
//            val rechargeAmount = editTextRechargeAmount.text.toString().toDouble()
//
//            userAccountRef.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        val userAccount = dataSnapshot.getValue(UserAccount::class.java)
//                        if (userAccount != null) {
//                            // Deduct the recharge amount from the loan balance
//                            userAccount.loanBalance -= rechargeAmount
//
//                            // If there's an excess amount, add it to the available balance
//                            if (rechargeAmount > userAccount.loanBalance) {
//                                userAccount.availableBalance += (rechargeAmount - userAccount.loanBalance)
//                                userAccount.loanBalance = 0.0
//                            }
//
//                            // Update the user account details in the database
//                            userAccountRef.setValue(userAccount)
//                        }
//                    }
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    // Handle errors if needed
//                }
//            })
//        }
}
}
