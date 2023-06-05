package com.example.expensetracker

import android.content.Context

class SharedPrefUtil(context: Context) {

    companion object {
        private const val INCOME = "income"
        private const val EXPENSE = "expense"
    }

    private val sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    val getIncome: Int = sharedPreferences.getInt(INCOME, 0)
    val getExpense: Int = sharedPreferences.getInt(EXPENSE, 0)

    fun addIncome(money: Int) {
        val income = getIncome

        sharedPreferences.edit().apply {
            putInt(INCOME, income + money)
            apply()
        }
    }

    fun addExpense(money: Int) {
        val expense = getExpense

        sharedPreferences.edit().apply {
            putInt(EXPENSE, expense + money)
            apply()
        }
    }

}