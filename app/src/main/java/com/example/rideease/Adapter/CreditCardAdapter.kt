package com.example.rideease.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rideease.Modals.AddCardModal
import com.example.rideease.R


class CreditCardAdapter(
    private val testResults: List<AddCardModal>,
    private val onDeleteClick: (AddCardModal) -> Unit
) : RecyclerView.Adapter<CreditCardAdapter.TestResultViewHolder>() {

    class TestResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardNumberTextView: TextView = itemView.findViewById(R.id.cardNumberTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val expirationMonthTextView: TextView = itemView.findViewById(R.id.expirationMonthTextView)
        val expirationYearTextView: TextView = itemView.findViewById(R.id.expirationYearTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestResultViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_credit_card, parent, false)
        return TestResultViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TestResultViewHolder, position: Int) {
        val testResult = testResults[position]

        // Set the data from AddCardModal to the views in the layout
        holder.cardNumberTextView.text = testResult.cardNumber
        holder.nameTextView.text = testResult.name
        holder.expirationMonthTextView.text = "Expiry Date: ${testResult.expirationMonth}"
        holder.expirationYearTextView.text = "/${testResult.expirationYear}"

        // Handle delete button click
        holder.deleteButton.setOnClickListener {
            onDeleteClick(testResult)
        }
    }

    override fun getItemCount(): Int {
        return testResults.size
    }
}


