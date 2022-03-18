package com.demo.webrtc.utils

import android.app.Activity
import android.app.ActivityManager
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.demo.newsapplication.base.BaseAdapter
import com.demo.newsapplication.utils.App
import com.demo.webrtc.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

fun getColorState(color: Int): ColorStateList =
        ColorStateList.valueOf(ContextCompat.getColor(App.getInstance(), color))

fun getCompactColor(@ColorRes color: Int) = ContextCompat.getColor(App.getInstance(), color)

fun getCompactDrawable(@DrawableRes drawable: Int) =
        ContextCompat.getDrawable(App.getInstance(), drawable)

fun View.gone() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

fun View.switchVisibility() {
    visibility = if (visibility == View.GONE) {
        View.VISIBLE
    } else {
        View.GONE
    }
}


fun Context.createAppDialog(@LayoutRes dialogLayout: Int): Dialog {
    val dialog = Dialog(this, R.style.ucrop_wideDialog)
    dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(dialogLayout)
    dialog.setCanceledOnTouchOutside(true)
    return dialog
}


fun changeDateFormat(Data: String, CurrentFormat: String, ChangeFormat: String): String {
    var change: String
    var sdf = SimpleDateFormat(CurrentFormat, Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    val date1: Date?
    try {
        date1 = sdf.parse(Data) ?: Date()
        sdf.timeZone = TimeZone.getDefault()
        sdf = SimpleDateFormat(ChangeFormat, Locale.getDefault())
        change = sdf.format(date1)
    } catch (e: ParseException) {
        e.printStackTrace()
        change = ""
    }
    return change
}

fun <T> Context.isServiceRunning(service: Class<T>): Boolean {
    return (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == service.name }
}




fun String.getSystemDateFromUTCDate(outFormat: String): String {
    if (isNullOrEmpty()) return ""
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val utcDate = formatter.parse(split("\\.")[0].replace("T", " "))

        val dateFormatter =
                SimpleDateFormat(outFormat, Locale.getDefault()) //this format changeable
        dateFormatter.timeZone = TimeZone.getDefault()
        dateFormatter.format(utcDate)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}



fun File.multipartImageBody(paramName: String): MultipartBody.Builder {
    val builder = MultipartBody.Builder()
    builder.setType(MultipartBody.FORM)
    builder.addFormDataPart(
            paramName,
            this.name.replace("-", "_"),
            asRequestBody("image/*".toMediaTypeOrNull())
    )
    return builder
}

fun View.visible() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

fun View.inVisible() {
    if (visibility != View.INVISIBLE)
        visibility = View.INVISIBLE
}


enum class ToastType {
    TOAST_SUCCESS,
    TOAST_ERROR,
    TOAST_WARNING
}

fun EditText.isBlank(): Boolean {
    return this.text.toString().isBlank()
}

val String.isEmailValid: Boolean
    get() {
        val pattern: Pattern
        val emailPattern =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(emailPattern)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }

private var toast: Toast? = null




fun RecyclerView.setAdapter(adapter: RecyclerView.Adapter<*>?, emptyView: View?) {
    val observer = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty(emptyView)
            super.onChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkIfEmpty(emptyView)
            super.onItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty(emptyView)
            super.onItemRangeRemoved(positionStart, itemCount)
        }
    }

    this.adapter?.unregisterAdapterDataObserver(observer)
    this.adapter = adapter
    if (adapter is BaseAdapter<*, *>) {
        adapter.setEmptyView(emptyView)
    }
    adapter?.registerAdapterDataObserver(observer)
    checkIfEmpty(emptyView)
}

fun RecyclerView.checkIfEmpty(emptyView: View?) {
    if (emptyView != null && adapter != null) {
        val emptyViewVisible = adapter?.itemCount == 0
        emptyView.isVisible = emptyViewVisible
        isVisible = !emptyViewVisible
    }
}



fun EditText.openCalender(context: Context) {
    val myCalendar: Calendar = Calendar.getInstance()
    val date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        this.setText(SimpleDateFormat("yyyy-MM-dd", Locale.US).format(myCalendar.time))
    }


}



private fun Activity.getAppDirectory(): File {
    val mediaDir = externalMediaDirs.firstOrNull()?.let {
        File(it.absolutePath + "/Muzville").apply { mkdirs() }
    }

    return if (mediaDir != null && mediaDir.exists())
        mediaDir else filesDir
}