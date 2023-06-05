package com.example.expensetracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecordAdapter(private val dataSet: List<RecordEntity>) :
    RecyclerView.Adapter<RecordViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.record_row, viewGroup, false)

        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecordViewHolder, position: Int) {
        viewHolder.money.text = dataSet[position].amount.toString()
        viewHolder.date.text = dataSet[position].date
    }

    override fun getItemCount() = dataSet.size

}