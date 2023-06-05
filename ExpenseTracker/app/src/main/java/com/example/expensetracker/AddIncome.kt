package com.example.expensetracker

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_income.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class AddIncome : AppCompatActivity(), CoroutineScope {

    private val context = this

    private var job: Job = Job()

    private var isDateSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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

    fun saveIncome(view: View) {

        val amountText = textFieldIncome.editText?.text.toString()

        if (amountText.isEmpty()) {

            textFieldDate.error = null

            textFieldIncome.error = "Enter amount"
        } else if (!isDateSelected) {

            textFieldIncome.error = null

            textFieldDate.error = "Select date"
        } else {

            textFieldIncome.error = null
            textFieldDate.error = null

            val amount = amountText.toInt()
            val date = textFieldDate.editText?.text.toString()

            val entity = RecordEntity(0, RecordType.Income.toString(), "", date, amount)

            launch {
                AppDatabase.getDatabase(context).recordDAO().insertOrUpdate(entity)

                SharedPrefUtil(this@AddIncome).addIncome(amount)

                finish()
            }

        }

    }
}