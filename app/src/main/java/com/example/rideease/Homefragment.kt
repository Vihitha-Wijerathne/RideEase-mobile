package com.example.rideease

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.rideease.AddLoan.AddLoan


class Homefragment : Fragment() {
    private lateinit var getloanbtn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_homefragment, container, false)

        getloanbtn = view.findViewById(R.id.getloan_home)

        getloanbtn.setOnClickListener{
            val intent = Intent(context,AddLoan::class.java)
            startActivity(intent)
        }

        return view
    }


}