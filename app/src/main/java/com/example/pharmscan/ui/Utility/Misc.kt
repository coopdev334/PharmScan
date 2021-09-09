package com.example.pharmscan.ui.Utility

import androidx.core.text.isDigitsOnly

// Determine is string value is valid decimal numeric with 1 decimal place
fun isDecNumber(s: String?): Boolean {
    if (s.isNullOrEmpty()) {
        return false
    } else {
        return s.matches("""^(\d+\.{1}\d{1})$""".toRegex())
    }
}

// Determine is string value is valid whole number no decimals
fun isWholeNumber(s: String?): Boolean {
    if (s.isNullOrEmpty()) {
        return false
    } else {
        return s.matches("""^(0|[1-9]\d*)$""".toRegex())
    }
}