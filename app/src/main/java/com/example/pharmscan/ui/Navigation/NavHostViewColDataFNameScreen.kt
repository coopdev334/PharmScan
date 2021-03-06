package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Screen.Screen
import java.time.LocalDateTime

fun NavGraphBuilder.addViewColDataFNameScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    composable(Screen.ViewColDataFNameScreen.route) {
        val scroll = rememberScrollState(0)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
            }
            Text(
                text = "<-",
                fontSize = 40.sp,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "Collected Data",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = "File Name",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(height = 30.dp))
            Box(
                Modifier
                    .height(45.dp)
                    .width(290.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    // File name: app_opid_date_unitId.new
                    text = GetCollectedDataFileName(pharmScanViewModel),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.horizontalScroll(scroll)
                )
            }
        }
    }
}

fun GetCollectedDataFileName(
    pharmScanViewModel: PharmScanViewModel
) : String {
    // TODO: tempory file name display since file creation is not completed yet.
    val secs = (LocalDateTime.now().second + (LocalDateTime.now().minute*60) + (LocalDateTime.now().hour*3600))
    val systemInfo = pharmScanViewModel.getSystemInfoRow()

    if (systemInfo.isNotEmpty()) {
        return "pharmscan_" + systemInfo[0].opid + "_" + LocalDateTime.now().monthValue + "-" + LocalDateTime.now().dayOfMonth + "-" + LocalDateTime.now().year + "_" + secs + "_" + systemInfo[0].HHDeviceId + ".new"
    }
    else {
        return "No data in SystemInfo Table"
    }
}