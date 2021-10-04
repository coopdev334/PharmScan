package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.pharmscan.PharmScanApplication
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Screen.*
import com.example.pharmscan.ui.Utility.writeToFile
import kotlinx.coroutines.runBlocking
import java.util.*

@ExperimentalComposeUiApi
fun NavGraphBuilder.addResetDatabaseScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    composable(route = Screen.ResetDatabaseScreen.route) {
        var checkedStateColData by remember { mutableStateOf(false) }
        var checkedStateHostIpAddress by remember { mutableStateOf(false) }
        var checkedStateNdc by remember { mutableStateOf(false) }
        var checkedStateSettings by remember { mutableStateOf(false) }
        var checkedStateSystemInfo by remember { mutableStateOf(false) }


        fun onOkClick() {
            if (checkedStateColData) {
                runBlocking {
                    val time: Date = Calendar.getInstance().getTime()
                    writeToFile("Deleted All Collected Data at " + time.toString(), PharmScanApplication.context)
                    val job = pharmScanViewModel.deleteAllCollectedData()
                    job.join()
                }

            }

            if (checkedStateHostIpAddress) {
                runBlocking {
                    val time: Date = Calendar.getInstance().getTime()
                    writeToFile("Deleted All HostIP's Data at " + time.toString(), PharmScanApplication.context)
                    val job = pharmScanViewModel.deleteAllHostIpAddress()
                    job.join()
                }
            }

            if (checkedStateNdc) {
                runBlocking {
                    val time: Date = Calendar.getInstance().getTime()
                    writeToFile("Cleared the PSNDC table at " + time.toString(), PharmScanApplication.context)
                    val job = pharmScanViewModel.deleteAllPSNdc()
                    job.join()
                }
            }

            if (checkedStateSettings) {
                runBlocking {
                    val time: Date = Calendar.getInstance().getTime()
                    writeToFile("Cleared the settings table at " + time.toString(), PharmScanApplication.context)
                    val job = pharmScanViewModel.deleteAllSettings()
                    job.join()
                }
            }

            if (checkedStateSystemInfo) {
                runBlocking {
                    val time: Date = Calendar.getInstance().getTime()
                    writeToFile("Cleared the SystemInfo table at " + time.toString(), PharmScanApplication.context)
                    val job = pharmScanViewModel.deleteAllSystemInfo()
                    job.join()
                }
            }

            navController.popBackStack()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Select Tables to Reset",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Collected Data",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                //Spacer(modifier = Modifier.width(width = 40.dp))
                Checkbox(
                    modifier = Modifier.size(width = 40.dp, height = 20.dp),
                    checked = checkedStateColData,
                    onCheckedChange = {checkedStateColData = it}
                )
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "HostComputerName",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                //Spacer(modifier = Modifier.width(width = 10.dp))
                Checkbox(
                    modifier = Modifier.size(width = 40.dp, height = 20.dp),
                    checked = checkedStateHostIpAddress,
                    onCheckedChange = {checkedStateHostIpAddress = it}
                )
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ndc",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                //Spacer(modifier = Modifier.width(width = 70.dp))
                Checkbox(
                    modifier = Modifier.size(width = 40.dp, height = 20.dp),
                    checked = checkedStateNdc,
                    onCheckedChange = {checkedStateNdc = it}
                )
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                //Spacer(modifier = Modifier.width(width = 40.dp))
                Checkbox(
                    modifier = Modifier.size(width = 40.dp, height = 20.dp),
                    checked = checkedStateSettings,
                    onCheckedChange = {checkedStateSettings = it}
                )
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SystemInfo",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                //Spacer(modifier = Modifier.width(width = 40.dp))
                Checkbox(
                    modifier = Modifier.size(width = 40.dp, height = 20.dp),
                    checked = checkedStateSystemInfo,
                    onCheckedChange = {checkedStateSystemInfo = it}
                )
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 8.dp))
            Text(
                text = "WARNING! Selected tables",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.error
            )
            Text(
                text = " will have all data deleted",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.error
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                TextButton(
                    modifier = Modifier.size(width = 90.dp, height = 45.dp),
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.h5
                    )
                }
                Button(
                    modifier = Modifier.size(width = 90.dp, height = 45.dp),
                    onClick = ::onOkClick,
                ) {
                    Text(
                        text = " Reset ",
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}
