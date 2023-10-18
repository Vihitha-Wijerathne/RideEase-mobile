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
    private val creditCards: List<AddCardModal>,
    private val onDeleteClick: (AddCardModal) -> Unit
) : RecyclerView.Adapter<CreditCardAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardNumberTextView: TextView = itemView.findViewById(R.id.cardNumberTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val expirationMonthTextView: TextView = itemView.findViewById(R.id.expirationMonthTextView)
        val expirationYearTextView: TextView = itemView.findViewById(R.id.expirationYearTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(creditCard: AddCardModal, onDeleteClick: (AddCardModal) -> Unit) {
            cardNumberTextView.text = creditCard.cardNumber
            nameTextView.text = creditCard.name
            expirationMonthTextView.text = "Expiry Date: ${creditCard.expirationMonth}"
            expirationYearTextView.text = "/${creditCard.expirationYear}"

            // Handle delete button click
            deleteButton.setOnClickListener {
                onDeleteClick(creditCard)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_credit_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val creditCard = creditCards[position]
        holder.bind(creditCard, onDeleteClick)
    }

    override fun getItemCount(): Int {
        return creditCards.size
    }
}
