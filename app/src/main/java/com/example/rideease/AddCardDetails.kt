package com.example.rideease

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
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

        cardNumber.filters = arrayOf(InputFilter.LengthFilter(19))
        cvv.filters = arrayOf(InputFilter.LengthFilter(3))
        expirationMonth.filters = arrayOf(InputFilter.LengthFilter(2))
        expirationYear.filters = arrayOf(InputFilter.LengthFilter(2))

        cardNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                val originalText = editable.toString().replace(" ", "")
                val formattedText = StringBuilder()
                for (i in originalText.indices) {
                    formattedText.append(originalText[i])
                    if ((i + 1) % 4 == 0 && i != originalText.length - 1) {
                        formattedText.append(" ")
                    }
                }
                cardNumber.removeTextChangedListener(this)
                cardNumber.setText(formattedText)
                cardNumber.setSelection(formattedText.length)
                cardNumber.addTextChangedListener(this)
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })

        submitBtn.setOnClickListener {
            saveCreditCard()
        }
    }

    private fun saveCreditCard() {
        val nameText = Name.text.toString()
        val cardNumberText = cardNumber.text.toString().replace(" ", "") // Remove spaces
        val expirationMonthText = expirationMonth.text.toString()
        val expirationYearText = expirationYear.text.toString()
        val cvvText = cvv.text.toString()

        // Validate credit card details
        if (nameText.isEmpty() || nameText.length < 2 ||
            cardNumberText.isEmpty() || cardNumberText.length != 19 ||
            expirationMonthText.isEmpty() || !isValidExpirationDate(expirationMonthText) ||
            expirationYearText.isEmpty() || !isValidExpirationDate(expirationYearText) ||
            cvvText.isEmpty() || cvvText.length != 3
        ) {
            // Display an error toast if validation fails
            Toast.makeText(this, "Invalid credit card details", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if the user is authenticated
        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        if (user == null) {
            // Display an error toast if the user is not authenticated
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        // Get the user's UID
        val uid = user.uid

        // Create a credit card object
        val creditCard = AddCardModal(uid, nameText, cardNumberText, expirationYearText, expirationMonthText, cvvText)

        // Save credit card data to the Firebase Realtime Database
        val cardId = dbRef.push().key
        if (cardId != null) {
            dbRef.child(cardId).setValue(creditCard)
                .addOnCompleteListener {
                    // Display a success toast
                    Toast.makeText(this, "Credit card added successfully", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
                .addOnFailureListener { e ->
                    // Display an error toast in case of failure
                    Toast.makeText(this, "Failed to add credit card: ${e.message}", Toast.LENGTH_SHORT).show()
                }
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
