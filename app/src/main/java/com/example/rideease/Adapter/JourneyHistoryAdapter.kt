package com.example.rideease.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rideease.Modals.JourneyHistoryModal
import com.example.rideease.R


class JourneyHistoryAdapter(private val testResults: List<JourneyHistoryModal>,
                            private val onDeleteClickListener: (JourneyHistoryModal) -> Unit) :
    RecyclerView.Adapter<JourneyHistoryAdapter.TestResultViewHolder>() {

    // Create a ViewHolder class to hold the views for a single row
    class TestResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val destinationTextView: TextView = itemView.findViewById(R.id.destinationTextView)
        val LocationTextView: TextView = itemView.findViewById(R.id.LocationTextView)
        val AmountTextView: TextView = itemView.findViewById(R.id.AmountTextView)
        val DateTextView: TextView = itemView.findViewById(R.id.DateTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        // Add more views as needed
    }

    // Create the ViewHolder and inflate the layout for each row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestResultViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_journey_history, parent, false) // Create an XML layout for a single row
        return TestResultViewHolder(itemView)
    }

    // Bind data to the views in each row
    override fun onBindViewHolder(holder: TestResultViewHolder, position: Int) {
        val testResult = testResults[position]

        // Bind the data to the views
        holder.destinationTextView.text = testResult.destination
        holder.LocationTextView.text = testResult.Location
        holder.AmountTextView.text = "Score: ${testResult.amount}"
        holder.DateTextView.text = "Score: ${testResult.Date}"
        // Add more binding as needed

        holder.deleteButton.setOnClickListener {
            onDeleteClickListener(testResult)
        }
    }

    // Return the number of items in the data set
    override fun getItemCount(): Int {
        return testResults.size
    }
}