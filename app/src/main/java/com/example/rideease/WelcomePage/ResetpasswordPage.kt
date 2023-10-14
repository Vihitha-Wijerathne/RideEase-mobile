package com.example.rideease.WelcomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.rideease.R
import com.google.firebase.auth.FirebaseAuth

class ResetpasswordPage : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private  lateinit var noAcc: Button
    private lateinit var resetPwdButton: Button
    private lateinit var resetEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resetpassword_page)

        firebaseAuth = FirebaseAuth.getInstance()

        noAcc = findViewById(R.id.)
        resetPwdButton = findViewById(R.id.)
        resetEmail = findViewById(R.id.)

        noAcc.setOnClickListener{
            val intent = Intent(this, SignUpPage::class.java)
            startActivity(intent)
        }

        resetPwdButton.setOnClickListener{
           val email = resetEmail.text.toString()

            if (email.isNotEmpty()) {
                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Email sent Unsuccessful!", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Incorrect Email!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}