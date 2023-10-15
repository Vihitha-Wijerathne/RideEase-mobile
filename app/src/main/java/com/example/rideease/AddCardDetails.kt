package com.example.rideease

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rideease.Modals.AddCardModal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddCardDetails : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var Name: EditText
    private lateinit var cardNumber: EditText
    private lateinit var expirationMonth: EditText
    private lateinit var expirationYear: EditText
    private lateinit var cvv: EditText
    private lateinit var submitBtn: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private var vCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_credit_card)

        // Initialize Firebase Database reference
        dbRef = FirebaseDatabase.getInstance().getReference("CreditCards")

        Name = findViewById(R.id.name)
        cardNumber = findViewById(R.id.card_number)
        expirationMonth = findViewById(R.id.month)
        expirationYear = findViewById(R.id.year)
        cvv = findViewById(R.id.cvv)
        submitBtn = findViewById(R.id.submit_button)
        vCount = 0

        submitBtn.setOnClickListener {
            saveCreditCard()
        }
    }

    private fun saveCreditCard() {

        val nameText = Name.text.toString()
        val cardNumberText = cardNumber.text.toString()
        val expirationMonthText = expirationMonth.text.toString()
        val expirationYearText = expirationYear.text.toString()
        val cvvText = cvv.text.toString()

        if (nameText .isEmpty() || cardNumberText.length < 16) {
            cardNumber.error = "Invalid Name"
            vCount++
        }

        if (cardNumberText.isEmpty() || cardNumberText.length < 16) {
            cardNumber.error = "Invalid card number"
            vCount++
        }

        if (expirationMonthText.isEmpty() || !isValidExpirationDate(expirationMonthText)) {
            expirationMonth.error = "Invalid expiration month"
            vCount++
        }

        if (expirationYearText.isEmpty() || !isValidExpirationDate(expirationYearText)) {
            expirationYear.error = "Invalid expiration year"
            vCount++
        }

        if (cvvText.isEmpty() || cvvText.length != 3) {
            cvv.error = "Invalid CVV"
            vCount++
        }

        if (vCount == 0) {
            firebaseAuth = FirebaseAuth.getInstance()
            val user = firebaseAuth.currentUser

            user?.let {
                val uid = it.uid

                val creditCard = AddCardModal(uid, nameText ,cardNumberText, expirationYearText, expirationMonthText , cvvText)

                // Save credit card data to the Firebase Realtime Database
                val cardId = dbRef.push().key
                if (cardId != null) {
                    dbRef.child(cardId).setValue(creditCard)
                        .addOnCompleteListener {
                            Toast.makeText(this, "Credit card added successfully", Toast.LENGTH_SHORT).show()
                            clearFields()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to add credit card: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        } else {
            vCount = 0
        }
    }

    private fun isValidExpirationDate(expirationDate: String): Boolean {
        // Implement your own validation logic for expiration dates (e.g., MM/YY)
        // Return true if the date is valid, false otherwise
        // Add your validation logic here
        return true
    }

    private fun clearFields() {

        Name.text.clear()
        cardNumber.text.clear()
        expirationYear.text.clear()
        expirationMonth.text.clear()
        cvv.text.clear()
    }
}

