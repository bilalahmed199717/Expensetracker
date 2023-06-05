package com.example.expensetracker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecordViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val money = view.findViewById<TextView>(R.id.textViewMoney)
    val date = view.findViewById<TextView>(R.id.textViewDate)
}