package com.example.pharmscan.ui.Utility

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import android.widget.Toast
import com.example.pharmscan.PharmScanApplication

// Determine is string value is valid decimal numeric with 1 decimal place
fun is1DecNumber(s: String?): Boolean {
    if (s.isNullOrEmpty()) {
        return false
    } else {
        return s.matches("""^(\d+\.{1}\d{1})$""".toRegex())
    }
}

// Determine is string value is valid decimal numeric with 2 decimal places
fun is2DecNumber(s: String?): Boolean {
    if (s.isNullOrEmpty()) {
        return false
    } else {
        return s.matches("""^(\d+\.{1}\d{2})$""".toRegex())
    }
}

// Determine is string value is valid whole number no decimals
fun isNotWholeNumber(s: String?): Boolean {
    if (s.isNullOrEmpty()) {
        return false
    } else {
        return !(s.matches("""^(0|[0-9]\d*)$""".toRegex()))
    }
}

// Warning: If calling Toast from a coroutine, ONLY Main dispatcher can be used.
// Cannot be called from IO or Default
fun ToastDisplay(message: String, length: Int): Toast? {
    val con = PharmScanApplication()
    var t: Toast? = null
    if (con.getAppContext() != null) {
        t =  Toast.makeText(con.getAppContext(), message, length)
        t.show()
    }

    return t
}
