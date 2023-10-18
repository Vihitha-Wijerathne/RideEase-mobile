package com.example.rideease

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.rideease.GetLoan.AddLoan
import com.example.rideease.Modals.UserModal
import com.example.rideease.Recharge.Recharge
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


    class
    Userfragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var usernametxt: TextView
    private lateinit var email: TextView
    private lateinit var contactNumb: TextView
    private lateinit var cbalance: TextView
    private lateinit var nic: TextView
    private lateinit var logintxtbtn: Button
    private lateinit var rechargebtn: Button
    private lateinit var loanhistory: Button
    private lateinit var qrcode: Button

        private val TAG = "MyActivity"

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_userfragment, container, false)


        var userid = ""
        usernametxt = view.findViewById(R.id.username_profile)
        email = view.findViewById(R.id.email_profile)
        contactNumb = view.findViewById(R.id.number_profile)
        cbalance = view.findViewById(R.id.due_loan)
        nic = view.findViewById(R.id.nic_profile)
        logintxtbtn = view.findViewById(R.id.logintxt)
        rechargebtn = view.findViewById(R.id.rechargebtn)
        loanhistory = view.findViewById(R.id.loanhistory)
        qrcode = view.findViewById(R.id.qrcode)

        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser

        rechargebtn.setOnClickListener{
            val intent = Intent(activity, Recharge::class.java)
            startActivity(intent)
        }
        logintxtbtn.setOnClickListener{
            val intent = Intent(activity, AddLoan::class.java)
            startActivity(intent)
        }

        loanhistory.setOnClickListener{
            val intent = Intent(activity, LoanHistory::class.java)
            startActivity(intent)
        }

        qrcode.setOnClickListener{
            val intent = Intent(activity, QRCodeActivity::class.java)
            startActivity(intent)
        }


        user?.let{
            userid = it.uid

            database = FirebaseDatabase.getInstance().getReference("users").child(userid)
            database.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d(TAG, "inside data$userid retrieval")
                    if(snapshot.exists()){
                        val userresults = snapshot.getValue(UserModal::class.java)
                        nic.text = userresults?.nic
                        usernametxt.text = userresults?.name
                        email.text = userresults?.email
                        contactNumb.text = userresults?.number
                        cbalance.text = userresults?.balance.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context,"There is a problem of retrieving data from database", Toast.LENGTH_LONG).show()
                }
            })

        }

        return view

    }

}