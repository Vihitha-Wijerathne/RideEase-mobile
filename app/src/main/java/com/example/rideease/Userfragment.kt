package com.example.rideease

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.rideease.Modals.UserModal
import com.example.rideease.Recharge.Recharge
import com.example.rideease.WelcomePage.SignInPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



@Suppress("UNREACHABLE_CODE")
    class Userfragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var usernametxt: TextView
    private lateinit var email: TextView
    private lateinit var contactNumb: TextView
    private lateinit var cbalance: TextView
    private lateinit var nic: TextView
    private lateinit var logintxtbtn: Button
    private lateinit var rechargebtn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_userfragment, container, false)

        var userid = ""
//        usernametxt = view.findViewById(R.id.username_profile)
        email = view.findViewById(R.id.email_profile)
        contactNumb = view.findViewById(R.id.number_profile)
        cbalance = view.findViewById(R.id.cbalance_profile)
        nic = view.findViewById(R.id.nic_profile)
        logintxtbtn = view.findViewById(R.id.logintxt)
        rechargebtn = view.findViewById(R.id.rechargebtn)
        firebaseAuth = FirebaseAuth.getInstance()

        val user = firebaseAuth.currentUser

        rechargebtn.setOnClickListener{
            val intent = Intent(activity, Recharge::class.java)
            startActivity(intent)
        }
        logintxtbtn.setOnClickListener{
            val intent = Intent(activity, SignInPage::class.java)
            startActivity(intent)
        }

        user?.let{
            userid = it.uid

            database = FirebaseDatabase.getInstance().getReference("users").child(userid)
            database.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val user = snapshot.getValue(UserModal::class.java)
                        nic.text = user?.nic
                        usernametxt.text = user?.name
                        email.text = user?.email
                        contactNumb.text = user?.number
                       // cbalance.text = user?.balance
66
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