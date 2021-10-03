package com.example.pharmscan.ui.Utility

import com.example.pharmscan.Data.Tables.SystemInfo
import com.example.pharmscan.ViewModel.PharmScanViewModel
import kotlinx.coroutines.runBlocking
import java.math.RoundingMode
import java.text.DecimalFormat

// update column values in SystemInfo table. ONLY 1 row is allowed.
// Save current SystemInfo data, then deleteRow the 1 row and then insert new row with
// updated column values
fun UpdateSystemInfo(pharmScanViewModel: PharmScanViewModel, columnValue: Map<String, String>? = null) {
    // Get the current SystemInfo values
    var systemInfo = pharmScanViewModel.getSystemInfoRow()

    runBlocking {

        if (systemInfo.isEmpty() || columnValue.isNullOrEmpty()) {
            // set to defaults then update columns
            systemInfo = listOf(SystemInfo("0", "0", "0", "0.0", "0.00", "0", "0", "0", "off"))
        } else {
            val job = pharmScanViewModel.deleteAllSystemInfo()
            job.join() // wait for deleteRow to complete
        }
    }


    if (!columnValue.isNullOrEmpty()) {
        for (item in columnValue) {
            when (item.key) {
                "hostIpAddress" -> systemInfo[0].hostIpAddress = item.value
                "opid" -> systemInfo[0].opid = item.value
                "HHDevice" -> systemInfo[0].HHDeviceId = item.value
                "TotQty" -> systemInfo[0].TotQty = item.value
                "TotAmt" -> systemInfo[0].TotAmt = item.value
                "TotRecCount" -> systemInfo[0].TotRecCount = item.value
                "Tag" -> {
                    if (systemInfo[0].Tag != "0" && item.value != systemInfo[0].Tag){
                        val tagCnt = systemInfo[0].TagChangeCount?.toInt()
                        val cnt = tagCnt?.plus(1)
                        systemInfo[0].TagChangeCount = cnt.toString()
                    }
                    systemInfo[0].Tag = item.value

                }
                "NdcLoading" -> systemInfo[0].NdcLoading = item.value
            }
        }
    }

    // Update qty and qty*price totals in SystemInfo table. On any updates to SystemInfo
    // always get new totals into table for current Tag.
    var totqty = 0.0
    var totamt = 0.0
    val dfq = DecimalFormat("######0.0") // formatter object
    val dfp = DecimalFormat("######0.00") // formatter object
    dfq.roundingMode = RoundingMode.FLOOR  // take 1 decimal position as is
    dfp.roundingMode = RoundingMode.FLOOR  // take 2 decimal position as is

    val qtyPriceList = pharmScanViewModel.getColDataQtyPriceByTag(systemInfo[0].Tag!!)

    if (!qtyPriceList.isNullOrEmpty()) {
        for (row in qtyPriceList) {
            if (!row.qty.isNullOrEmpty()) totqty += row.qty!!.toDouble()
            if (!row.price.isNullOrEmpty()) totamt += row.price!!.toDouble() * row.qty!!.toDouble()
        }
        systemInfo[0].TotQty = dfq.format(totqty)
        systemInfo[0].TotAmt = dfp.format(totamt)
    }else{
        systemInfo[0].TotQty = "0.0"
        systemInfo[0].TotAmt = "0.00"
    }

    runBlocking {
        val job = pharmScanViewModel.insertSystemInfo(systemInfo[0])
        job.join()
    }
}
