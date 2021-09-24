package com.example.pharmscan.ui.Navigation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.Data.Tables.PSNdc
import com.example.pharmscan.Data.Tables.Settings
import com.example.pharmscan.Data.Tables.SystemInfo
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Dialog.GetOpId
import com.example.pharmscan.ui.Screen.Screen
import com.example.pharmscan.ui.Utility.ToastDisplay
import com.example.pharmscan.ui.Utility.UpdateSystemInfo
import kotlinx.coroutines.*
import java.io.*
import java.net.Socket
import android.system.Os.socket

import android.R
import android.content.Intent
import android.util.Log

import android.widget.EditText
import com.example.pharmscan.Network.NetworkThread


@ExperimentalComposeUiApi
fun NavGraphBuilder.addPhysInvUploadScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {


    composable(
        route = Screen.PhysInvUploadScreen.route + "/{hostCompName}",
        // Define argument list to pass to this composable in composable constructor
        // arguments parameter which is a list of navArguments.
        arguments = listOf(
            navArgument("hostCompName") {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        // Get the arguments passed to this composable by key name
        // arg must be present
        val argTextHostComputer = it.arguments!!.getString("hostCompName")
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        val showEnterOpIdDialog = remember { mutableStateOf(false) }
        val settings: List<Settings> by pharmScanViewModel.settings.observeAsState(pharmScanViewModel.getSettingsRow())
        val tableOfData: List<CollectedData> by pharmScanViewModel.collectedData.observeAsState(pharmScanViewModel.getAllCollectedData())


        if (showEnterOpIdDialog.value) {
            GetOpId(
                showDialog = showEnterOpIdDialog.value,
                onCancel = {
                    showEnterOpIdDialog.value = false
                },
                onToScanScreen = {opid ->
                    showEnterOpIdDialog.value = false
                    val columnValue = mapOf("opid" to opid)
                    UpdateSystemInfo(pharmScanViewModel, columnValue)
                    val settings = pharmScanViewModel.getSettingsRow()
                    if (!settings.isNullOrEmpty()) {
                        if (settings[0].AutoLoadNdcFile == "on") {
                            // Check if already downloading. If currenlty still downloading
                            // don't start downloading again.
                            val sysInfo = pharmScanViewModel.getSystemInfoRow()
                            if (sysInfo[0].NdcLoading == "off") {
                                ToastDisplay("Downloading Started...", Toast.LENGTH_SHORT)
                                CoroutineScope(Dispatchers.IO).launch {
                                    readFileLineByLineUsingForEachLine(pharmScanViewModel,"/sdcard/Download/psndc.dat")
                                }
                            }
                        }
                    }

                    navController.navigate(Screen.ScanScreen.withArgs("*** Scan Tag ***", "yellow"))
                }
            )
        }

        // NOTE: Changes need to be made also in all screens with the scafford settings
        Scaffold(
            scaffoldState = scaffoldState,
            drawerShape = MaterialTheme.shapes.large,
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp)
                ) {
                    Text(
                        text = "Settings",
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                            }
                            navController.navigate(Screen.SettingsScreen.route)
                        },
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground
                    )

                    Spacer(modifier = Modifier.height(height = 10.dp))

                    Text(
                        text = "View Cancel",
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                                navController.navigate(Screen.ViewCancel.route) }
                        },
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground
                    )

                    Spacer(modifier = Modifier.height(height = 10.dp))

                    Text(
                        text = "View File Name",
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                                navController.navigate(Screen.ViewColDataFNameScreen.route)
                            }
                        },
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground
                    )

                    Spacer(modifier = Modifier.height(height = 10.dp))

                    Text(
                        text = "About",
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                                navController.navigate(Screen.AboutScreen.route)
                            }
                        },
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            },            topBar = {
                TopAppBar(
                    title = { Text("PharmScan") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch { scaffoldState.drawerState.open() }
                            }
                        ) {
                            Icon(Icons.Filled.Menu, contentDescription = "Localized description")
                        }
                    }
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.Top
                ){
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
                        text = argTextHostComputer!!,
                        fontSize = 40.sp,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(start = 10.dp),
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colors.secondary)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier.clip(RoundedCornerShape(50.dp)),
                            onClick = {
                            showEnterOpIdDialog.value = true
                        }) {
                            Text(
                                text = "Physical Inventory",
                                style = MaterialTheme.typography.h5,
                                color = MaterialTheme.colors.background
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier.clip(RoundedCornerShape(50.dp)),
                            onClick = {
                            // upload data using network wifi to host
//                                val con = PharmScanApplication()
//                                if (con.getAppContext() != null) {
//                                    Toast.makeText(con.getAppContext(), "kldsajflkjsdlfk", Toast.LENGTH_SHORT).show()
//                                }
                            pharmScanViewModel.uploadCollectedData()
                        }) {
                            Text(
                                text = "Upload Collected Data",
                                style = MaterialTheme.typography.h5,
                                color = MaterialTheme.colors.background,
                                modifier = Modifier.clickable {
                                    if (!settings.isEmpty()) {
                                        val hostAccountName = settings[0].hostAcct
                                        val hostAccountPassword = settings[0].hostPassword
                                        val sendingData = tableOfData
                                        val PORT = 2325;
                                        val query = "testquery"
                                        val nThread = NetworkThread()
                                        nThread.handleIncomingData(hostName = hostAccountName, hostPassword = hostAccountPassword,
                                            port = PORT, data = query)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}

suspend fun readFileLineByLineUsingForEachLine(pharmScanViewModel: PharmScanViewModel, fileName: String){
    var psndc = PSNdc("","","")

    //checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, 101)
    var sysInfoMap = mapOf("NdcLoading" to "on")
    UpdateSystemInfo(pharmScanViewModel, sysInfoMap)

    runBlocking {
        val job = pharmScanViewModel.deleteAllPSNdc()
        job.join()
    }

    File(fileName).forEachLine {
        psndc.ndc = it.substring(0..10)
        psndc.price = it.substring(11..16) + "." + it.substring(17..18)
        psndc.packsz = it.substring(19..26)

        runBlocking {
            val job = pharmScanViewModel.insertPSNdc(psndc)
            job.join()
        }
    }

    sysInfoMap = mapOf("NdcLoading" to "off")
    UpdateSystemInfo(pharmScanViewModel, sysInfoMap)

}