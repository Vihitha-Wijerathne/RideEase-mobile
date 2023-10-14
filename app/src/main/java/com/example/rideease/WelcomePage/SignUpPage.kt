package com.example.rideease.WelcomePage

import android.content.Context
import android.content.Intent
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

class SignUpPage : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var registerbtn: Button
    private lateinit var alredyhv: TextView
    private lateinit var rname: EditText
    private lateinit var rnic: EditText
    private lateinit var rpnumber: EditText
    private lateinit var remail: EditText
    private lateinit var raddress: EditText
    private lateinit var rpasswd: EditText
    private lateinit var repasswd: EditText
    private lateinit var rage: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        registerbtn
        alredyhv
        rname
        rnic
        rpnumber
        remail
        raddress
        rpasswd
        repasswd
        rage

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("users")

        alredyhv.setOnClickListener{
            val intent = Intent(this,SignInPage::class.java)
            startActivity(intent)
        }

        registerbtn.setOnClickListener{
            val name = rname.text.toString()
            val nic = rnic.text.toString()
            val number = rpnumber.text.toString()
            val email = remail.text.toString()
            val address = raddress.text.toString()
            val age = rage.text.toString()
            val password = rpasswd.text.toString()
            val repassword = repasswd.text.toString()

            if(nic.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repassword.isNotEmpty()){
                if(email.contains("@")){
                    if(password.length>=6){
                        if(nic.length.equals(12)){
                            if(password.equals(repassword)){

                                database.addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if (snapshot.exists()) {
                                            for (ResultsSnap in snapshot.children) {
                                                val Results = ResultsSnap.getValue(UserModal::class.java)
                                                if (Results?.nic == nic) {
                                                    Toast.makeText(this@SignUpPage,"user already exists", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        // Handle the error here
                                    }
                                })
                            }
                            else{
                                Toast.makeText(this,"Passwords are not matching", Toast.LENGTH_LONG).show()
                            }
                        }
                        else{
                            Toast.makeText(this,"NIC must have 12 numbers",Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
                        Toast.makeText(this,"Password must have 6 characters or more", Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    Toast.makeText(this,"email must contain @ sign", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(this,"NIC,emal and password must be filled",Toast.LENGTH_LONG).show()
            }



        }
    }
}