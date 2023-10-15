package com.example.rideease.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rideease.Modals.AddCardModal
import com.example.rideease.R


class CreditCardAdapter(private val testResults: List<AddCardModal>,
                                     private val onDeleteClickListener: (AddCardModal) -> Unit) :
    RecyclerView.Adapter<CreditCardAdapter.TestResultViewHolder>() {

    // Create a ViewHolder class to hold the views for a single row
    class TestResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val NameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val cardNumberTextView: TextView = itemView.findViewById(R.id.cardNumberTextView)
        val expirationMonthTextView: TextView = itemView.findViewById(R.id.expirationMonthTextView)
        val expirationYearTextView: TextView = itemView.findViewById(R.id.expirationYearTextView)
        // Add more views as needed
    }

    // Create the ViewHolder and inflate the layout for each row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestResultViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_credit_card, parent, false) // Create an XML layout for a single row
        return TestResultViewHolder(itemView)
    }

    // Bind data to the views in each row
    override fun onBindViewHolder(holder: TestResultViewHolder, position: Int) {
        val testResult = testResults[position]

        // Bind the data to the views
        holder.NameTextView.text = testResult.name
        holder.cardNumberTextView.text = testResult.cardNumber
        holder.expirationMonthTextView.text = testResult.expirationMonth
        holder.expirationYearTextView.text = testResult.expirationYear
        // Add more binding as needed

    }

    // Return the number of items in the data set
    override fun getItemCount(): Int {
        return testResults.size
    }
}

