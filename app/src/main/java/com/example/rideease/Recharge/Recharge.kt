package com.example.rideease.Recharge

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
    private lateinit var rechargebtn:Button
    private var rechargeamount: Double = 0.0
    private lateinit var inputamouhnt: EditText
    private lateinit var inputstring:String
    private lateinit var nic: String
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var number: String
    private lateinit var disbalance:TextView
    private lateinit var disloan:TextView
    private  var loana:Double = 0.0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge)

        usernametxt = findViewById(R.id.textViewUserName)
        avlbalancetxt = findViewById(R.id.textViewAvailableBalanceValue)
        loanbalancetxt = findViewById(R.id.textViewLoanBalanceValue)
        rechargebtn = findViewById(R.id.buttonRecharge)
        inputamouhnt = findViewById(R.id.editTextRechargeAmount)
        disbalance = findViewById(R.id.textViewAvailableBalanceValue)
        disloan = findViewById(R.id.textViewLoanBalanceValue)

        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        user?.let {

            userId = it.uid
            database = FirebaseDatabase.getInstance().getReference("users").child(userId)

            database.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userAccount = dataSnapshot.getValue(UserModal::class.java)
                        nic = userAccount?.nic!!
                        name = userAccount?.name!!
                        email = userAccount?.email!!
                        number = userAccount?.number!!
                        cbalance = userAccount?.balance!!
                        uloandue = userAccount?.loandue!!
                        disbalance.text = cbalance.toString()
                        loana = 1500.00-uloandue
                        disloan.text = loana.toString()

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Recharge,"There is a problem of retrieving data from database", Toast.LENGTH_LONG).show()
                }

            })
        }

        rechargebtn.setOnClickListener{
            inputstring = inputamouhnt.text.toString()
            try {
                rechargeamount = inputstring.toDouble()
            }catch (e : NumberFormatException){

            }
            recharge()

        }
    }

    private fun recharge(){
        if(loana<=rechargeamount){
            rechargeamount=rechargeamount-loana
            cbalance = cbalance + rechargeamount
            uloandue = 1500.0
        }
        else{
            loana = loana-rechargeamount
        }

        database = FirebaseDatabase.getInstance().getReference("users").child(userId)

        val updateuser = UserModal(nic,name,email,number,cbalance,uloandue)
        database.setValue(updateuser)
    }


}
