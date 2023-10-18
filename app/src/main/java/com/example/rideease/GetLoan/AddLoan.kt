package com.example.rideease.GetLoan

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.rideease.Modals.LoanModal
import com.example.rideease.Modals.UserModal
import com.example.rideease.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar

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
    private lateinit var loanresult: LoanModal
    private lateinit var date: String
    private lateinit var userid: String
    private lateinit var nic: String
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var number: String


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_loan)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        date = "$year/$month/$day"


        addbtn = findViewById(R.id.addloan_loan)
        lamount = findViewById(R.id.loanamount_loan)
        loandue = findViewById(R.id.remainingloan_loan)


        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser

        user?.let{
            userid = it.uid

            database = FirebaseDatabase.getInstance().getReference("users").child(userid)
            database.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val userr = snapshot.getValue(UserModal::class.java)

                        nic = userr?.nic!!
                        name = userr?.name!!
                        email = userr?.email!!
                        number = userr?.number!!
                        uloandue = userr?.loandue!!
                        cbalance = userr?.balance!!
                        loandue.text = uloandue.toString()

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AddLoan,"There is a problem of retrieving data from database", Toast.LENGTH_LONG).show()

                }
            })

        }


        addbtn.setOnClickListener{
            inputamount = lamount.text.toString()
            try {
                namount = inputamount.toDouble()
                addLoan()
            }catch (e : NumberFormatException){

            }

        }
    }

    private fun addLoan(){

        if(uloandue > namount){
            uloandue = uloandue - namount
            cbalance = cbalance + namount

            val user = firebaseAuth.currentUser
            var loanid = ""

            user?.let {
                val uid = it.uid

                database = FirebaseDatabase.getInstance().getReference("loan")

                loanid = database.push().key!!

                loanresult = LoanModal(loanid,uid,namount.toString(),date)

                database.child(loanid).setValue(loanresult)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Loan added Successfully", Toast.LENGTH_LONG).show()
                        lamount.text.clear()
                    }.addOnFailureListener { err ->
                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                    }
            }

            updateUser()
        }
        else{
            Toast.makeText(this,"please enter a amount lower than available loan due amount",Toast.LENGTH_LONG).show()
            lamount.text.clear()
        }

    }
    private fun updateUser(){

        database = FirebaseDatabase.getInstance().getReference("users").child(userid)

        val updateuser = UserModal(nic,name,email,number,cbalance,uloandue)
        database.setValue(updateuser)

    }
}