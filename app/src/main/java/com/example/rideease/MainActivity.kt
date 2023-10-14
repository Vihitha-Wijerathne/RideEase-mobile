package com.example.rideease

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private lateinit var homebtn: ImageButton
    private lateinit var userbtn: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homebtn = findViewById(R.id.homebtn)
        userbtn = findViewById(R.id.userprofile_btn)

        val homefrag = Homefragment()
        val userfrag = Userfragment()

        homebtn.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, homefrag)
                commit()
            }
        }

        userbtn.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, userfrag)
                commit()
            }
        }


    }
}