package com.example.rideease.WelcomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.rideease.MainActivity
import com.example.rideease.R
import com.google.firebase.auth.FirebaseAuth

class SignInPage : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private  lateinit var signinbtn: Button
    private lateinit var fogotpwdbtn: Button
    private lateinit var newacc: Button
    private lateinit var passwd: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_page)
        firebaseAuth = FirebaseAuth.getInstance()

        signinbtn = findViewById(R.id.)
        fogotpwdbtn = findViewById(R.id.)
        newacc = findViewById(R.id.)

        fogotpwdbtn.setOnClickListener{
            val intent = Intent(this,ResetpasswordPage::class.java)
            startActivity(intent)
        }

        newacc.setOnClickListener{
            val intent = Intent(this,SignUpPage::class.java)
            startActivity(intent)
        }

        signinbtn.setOnClickListener{
            val email = .text.toString()
            val pass = .text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        val user = firebaseAuth.currentUser
                        user?.let {

                            val name = it.displayName
                            val email = it.email
                            val photoUrl = it.photoUrl

                            val emailVerified = it.isEmailVerified

                            val uid = it.uid

                            println(name)
                            println(email)
                            println(photoUrl)
                            println(emailVerified)
                            println(uid)
                        }

                    } else {
                        Toast.makeText(this, "Email or Password is Incorrect", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this,"Empty Fields Are Not Allowed", Toast.LENGTH_SHORT).show()
            }
        }


    }
}