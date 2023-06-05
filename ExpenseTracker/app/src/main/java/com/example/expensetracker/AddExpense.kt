package com.example.expensetracker

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_expense.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class AddExpense : AppCompatActivity(), AdapterView.OnItemSelectedListener, CoroutineScope {

    private val context = this

    private var job: Job = Job()

    private val categoryList = listOf("Other", "Grocery", "Fuel", "Medicine", "Utility Bill")

    private var category: String = categoryList[0]

    private var isDateSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        setupSpinner()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        category = parent.selectedItem.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private fun setupSpinner() {
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categoryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = this
    }

    @SuppressLint("NewApi")
    fun openDatePicker(view: View) {

        val calendar = Calendar.getInstance()

        val listener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

                isDateSelected = true

                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = "dd-MM-YYYY"
                val dateFormatter = SimpleDateFormat(dateFormat)

                textFieldDate.editText?.setEditableText(dateFormatter.format(calendar.time))

            }

        DatePickerDialog(
            this,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()

    }

    fun saveExpense(view: View) {

        val amountText = textFieldExpense.editText?.text.toString()

        if (amountText.isEmpty()) {

            textFieldDate.error = null

            textFieldExpense.error = "Enter amount"
        } else if (!isDateSelected) {

            textFieldExpense.error = null

            textFieldDate.error = "Enter or select date"
        } else {

            textFieldExpense.error = null
            textFieldDate.error = null

            val amount = amountText.toInt()
            val date = textFieldDate.editText?.text.toString()

            val entity = RecordEntity(0, RecordType.Expense.toString(), category, date, amount)

            launch {
                AppDatabase.getDatabase(context).recordDAO().insertOrUpdate(entity)

                SharedPrefUtil(this@AddExpense).addExpense(amount)

                finish()
            }
        }

    }
}