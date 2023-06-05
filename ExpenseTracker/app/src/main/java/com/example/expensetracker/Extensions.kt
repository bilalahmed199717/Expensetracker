package com.example.expensetracker

import android.text.Editable
import android.widget.EditText

fun EditText.setEditableText(text: String) {
    this.text = Editable.Factory.getInstance().newEditable(text)
}
