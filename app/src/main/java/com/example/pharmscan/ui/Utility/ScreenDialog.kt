package com.example.pharmscan.ui.Utility

fun ConvertNumNativeKeyCodeToString(nativeKeyCode: Int): String {
    when (nativeKeyCode) {
        7 -> return "0"
        8 -> return "1"
        9 -> return "2"
        10 -> return "3"
        11 -> return "4"
        12 -> return "5"
        13 -> return "6"
        14 -> return "7"
        15 -> return "8"
        16 -> return "9"
    }
    return "0"
}

fun manageLength(input: String, length: Int) : String {
    var output: String

    if (input.toIntOrNull() == null) {
        return ""
    }
    if (input.length > length) {
        output = input.substring(0..length-1)
    }else {
        output = input
    }

    return output
}