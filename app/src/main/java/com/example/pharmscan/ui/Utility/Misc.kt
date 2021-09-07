package com.example.pharmscan.ui.Utility

// Determine is string value is valid numeric
fun isNumber(s: String): Boolean {
    return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
}