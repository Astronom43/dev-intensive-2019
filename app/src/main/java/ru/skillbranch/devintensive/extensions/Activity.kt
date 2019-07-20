package ru.skillbranch.devintensive.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

@SuppressLint("ServiceCast")
fun Activity.hideKeyboard(){

    val view = this.currentFocus
   view?.let { v ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }

}