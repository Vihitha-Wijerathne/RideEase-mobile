package com.example.rideease.AddLoan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.rideease.Modals.UserModal
import com.example.rideease.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddLoan : AppCompatActivity() {
    private lateinit var addbtn: Button
    private lateinit var lamount: EditText
    private lateinit var loandue: TextView
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private var uloandue: Double = 0.0
    private var cbalance: Double = 0.0
    private lateinit var inputamount: String
    private var namount: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_loan)

      //  addbtn = findViewById(R.id.)
      //  lamount = findViewById(R.id.)
     //   loandue = findViewById(R.id.)
        inputamount = lamount.text.toString()
        namount = inputamount.toDouble()


        firebaseAuth = FirebaseAuth.getInstance()
        getLoanDue()
        loandue.text = uloandue.toString()
        checkLoan()




    }

    private fun getLoanDue(){
        val user = firebaseAuth.currentUser

        user?.let{
            val userid = it.uid

            database = FirebaseDatabase.getInstance().getReference("users").child(userid)
            database.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val user = snapshot.getValue(UserModal::class.java)
                        uloandue = user?.loandue!!
                        cbalance = user?.balance!!

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AddLoan,"There is a problem of retrieving data from database", Toast.LENGTH_LONG).show()
                }
            })


        }
    }

    private fun checkLoan(){
        if(uloandue < namount){
            Toast.makeText(this,"please enter a amount lower than available loan due amount",Toast.LENGTH_LONG).show()
            lamount.text.clear()
        }
    }
    private fun addLoan(){

    }
}