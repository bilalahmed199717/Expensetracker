package com.example.expensetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_view_expenses.recyclerview
import kotlinx.android.synthetic.main.activity_view_expenses.tabLayout
import kotlinx.android.synthetic.main.activity_view_expenses.textViewTotal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ViewExpenses : AppCompatActivity(), CoroutineScope {

    private val context = this

    private var job: Job = Job()

    private val categoryList = listOf("All", "Other", "Grocery", "Fuel", "Medicine", "Utility Bill")

    private var category: String = categoryList[0]

    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        setupTabLayout()
        loadExpenses()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private fun setupTabLayout() {


        for (category in categoryList) {

            val tab = tabLayout.newTab()
            tab.text = category

            tabLayout.addTab(tab)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                category = categoryList[tabLayout.selectedTabPosition]

                loadExpenses()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }

    private fun loadExpenses() {

        launch {

            val dao = AppDatabase.getDatabase(context).recordDAO()

            val list = if (category == "All")
                dao.getAllExpenseRecords(RecordType.Expense.toString())
            else
                dao.getAllExpenseRecordsWithCategory(RecordType.Expense.toString(), category)

            total = 0

            for (record in list) {
                total += record.amount
            }

            recyclerview.adapter = RecordAdapter(list)

            textViewTotal.text = total.toString()
        }

    }
}