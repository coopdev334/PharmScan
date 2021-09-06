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

fun ManageLength(input: String, length: Int) : String {
    var output: String

    if (input.isEmpty()) {
        return ""
    }

    output = if (input.length > length) {
        input.substring(0..length-1)
    }else {
        input
    }

    return output
}

// Reformat text string when dialog receives a char captured before entering dialog. This is needed
// because cursor in text box is 1 behind character count
fun ReformatText(input: String): String {
    var output: String  = input

    if (input.isEmpty()) {
        return ""
    }
    when (output.length) {
        2 -> output = output[1].toString() + output[0].toString()
        3 -> output = output[0].toString() + output[2].toString() + output[1].toString()
        4 -> output = output[0].toString() + output[1].toString() + output[3].toString() + output[2].toString()
        5 -> output = output[0].toString() + output[1].toString() + output[2].toString() + output[4].toString() + output[3].toString()
        6 -> output = output[0].toString() + output[1].toString() + output[2].toString() + output[3].toString() + output[5].toString() + output[4].toString()
        7 -> output = output[0].toString() + output[1].toString() + output[2].toString() + output[3].toString() + output[4].toString() + output[6].toString() + output[5].toString()
        8 -> output = output[0].toString() + output[1].toString() + output[2].toString() + output[3].toString() + output[4].toString() + output[5].toString() + output[7].toString() + output[6].toString()
        9 -> output = output[0].toString() + output[1].toString() + output[2].toString() + output[3].toString() + output[4].toString() + output[5].toString() + output[6].toString() + output[8].toString() + output[7].toString()
        10 -> output = output[0].toString() + output[1].toString() + output[2].toString() + output[3].toString() + output[4].toString() + output[5].toString() + output[6].toString() + output[7].toString() + output[9].toString() + output[8].toString()
        11 -> output = output[0].toString() + output[1].toString() + output[2].toString() + output[3].toString() + output[4].toString() + output[5].toString() + output[6].toString() + output[7].toString() + output[8].toString() + output[10].toString() + output[9].toString()
    }

    return output
}