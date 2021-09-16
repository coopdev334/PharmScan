package com.example.pharmscan.ui.Screen

import com.example.pharmscan.R
import com.example.pharmscan.ui.Utility.isDecNumber
import com.example.pharmscan.ui.Utility.isNotWholeNumber

object InputValidator {

    fun getNdcErrorIdOrNull(input: String): Int? {
        return when {
            isNotWholeNumber(input) -> R.string.Numeric_Only
            input.length < 11 -> R.string.ndc_too_short
            else -> null
        }
    }

    fun getPkSzErrorIdOrNull(input: String): Int? {
        return when {
            isNotWholeNumber(input) -> R.string.Numeric_Only
            input.length < 1 -> R.string.Requires_min_1_number
            //etc..
            else -> null
        }
    }

    fun getPriceErrorIdOrNull(input: String): Int? {
        return when {
            !isDecNumber(input) -> R.string.missing_dec_pt
            else -> null
        }
    }

    fun getQtyErrorIdOrNull(input: String): Int? {
        return when {
            !isDecNumber(input) -> R.string.missing_dec_pt
             //etc..
            else -> null
        }
    }

}