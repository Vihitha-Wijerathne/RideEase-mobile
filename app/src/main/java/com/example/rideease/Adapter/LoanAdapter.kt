package com.example.rideease.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rideease.Modals.LoanModal
import com.example.rideease.R

class LoanAdapter(
    private val loanList: List<LoanModal>,
    private val onDeleteClick: (LoanModal) -> Unit
) : RecyclerView.Adapter<LoanAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val loanAmountTextView: TextView = itemView.findViewById(R.id.loanAmountTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton) // Changed view ID

        fun bind(loan: LoanModal, onDeleteClick: (LoanModal) -> Unit) {
            loanAmountTextView.text = "Rs.${loan.lamount}"
            dateTextView.text = loan.ldate

            // Handle delete button click
            deleteButton.setOnClickListener {
                onDeleteClick(loan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val loan = loanList[position]
        holder.bind(loan, onDeleteClick)
    }

    override fun getItemCount(): Int {
        return loanList.size
    }
}
