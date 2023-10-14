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
import com.google.firebase.database.IgnoreExtraProperties
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
    private lateinit var rpasswd: EditText
    private lateinit var repasswd: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        registerbtn = findViewById(R.id.reg_button)
        alredyhv = findViewById(R.id.already_acc)
        rname = findViewById(R.id.p_name)
        rnic = findViewById(R.id.reg_nic)
        rpnumber = findViewById(R.id.reg_phone)
        remail = findViewById(R.id.reg_email)
        rpasswd = findViewById(R.id.reg_password)
        repasswd = findViewById(R.id.re_password)


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
            val password = rpasswd.text.toString()
            val repassword = repasswd.text.toString()
            val balance = "0.0"

            if(nic.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repassword.isNotEmpty()){
                if(email.contains("@")){
                    if(password.length>=6){
                        if(nic.length.equals(12)){
                            if(password.equals(repassword)) {

                                firebaseAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {

                                            fun writeNewUser(
                                                userId: String,
                                                nic: String,
                                                name: String,
                                                email: String,
                                                phoneNumb: String,
                                                cbalance: String
                                            ) {
                                                val user = UserModal(nic, name, email, phoneNumb, cbalance)
                                                database.child(userId).setValue(user)
                                            }

                                            val user = firebaseAuth.currentUser
                                            user?.let {
                                                val uid = it.uid

                                                writeNewUser(uid, nic, name, email, number, balance)

                                                user?.sendEmailVerification()
                                                    ?.addOnCompleteListener(this@SignUpPage) { task ->
                                                        if (task.isSuccessful) {
                                                            Toast.makeText(
                                                                this@SignUpPage,
                                                                "Verification email sent to ${user.email}",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        } else {
                                                            Toast.makeText(
                                                                this@SignUpPage,
                                                                "Failed to send verification email.",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    }
                                            }

                                            val intent =
                                                Intent(this@SignUpPage, SignInPage::class.java)
                                            startActivity(intent)


                                        } else {
                                            Toast.makeText(this@SignUpPage, it.exception.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }

                            }else{
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