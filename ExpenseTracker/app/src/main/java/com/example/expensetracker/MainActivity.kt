package com.example.expensetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val sharedPref = SharedPrefUtil(this)
        val income = sharedPref.getIncome
        val expenses = sharedPref.getExpense

        val remaining = income - expenses

        textViewRemaining.text = remaining.toString()
        textViewExpenses.text = expenses.toString()
    }

    fun gotoAddIncomeActivity(view: View) {
        startActivity(Intent(this, AddIncome::class.java))
    }

    fun gotoAddExpenseActivity(view: View) {
        startActivity(Intent(this, AddExpense::class.java))
    }

    fun gotoViewExpensesActivity(view: View) {
        startActivity(Intent(this, ViewExpenses::class.java))
    }
}