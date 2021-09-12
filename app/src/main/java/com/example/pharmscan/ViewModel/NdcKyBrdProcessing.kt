package com.example.pharmscan.ViewModel

import android.widget.Toast
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.Data.Tables.PSNdc
import com.example.pharmscan.ui.Utility.ToastDisplay

fun KyBrdNdcSearch(ndc: String, pharmScanViewModel:PharmScanViewModel) {
    val result = pharmScanViewModel.getNdcPSNdc(ndc)

    if (result.isNullOrEmpty()) {
        ToastDisplay("No Match", Toast.LENGTH_LONG)
        // navigate no match screen to get data from user
    }else{
        // Insert new record into PSNdc table
        // TODO: get qty from user and other data
        val collectedData = CollectedData("111", "12345678", ndc, "123456", "12345678", "12345678", "123", "R", "1111", "123", "1001", "12/12/21", "123456", "P", "12345678")
        pharmScanViewModel.insertCollectedData(collectedData)
        ToastDisplay("Match: ", Toast.LENGTH_LONG)
    }
}