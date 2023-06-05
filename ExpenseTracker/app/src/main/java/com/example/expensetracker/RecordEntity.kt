package com.example.expensetracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class RecordType {
    Income, Expense
}

@Entity(tableName = "records")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @ColumnInfo val type: String,
    @ColumnInfo val category: String,
    @ColumnInfo val date: String,
    @ColumnInfo val amount: Int,
)
