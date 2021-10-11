package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ui.Screen.Screen
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.pharmscan.Data.Tables.HostIpAddress
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Dialog.AddHostIpAddress
import com.example.pharmscan.ui.Dialog.DeleteHostComputerAlert
import com.example.pharmscan.ui.Utility.UpdateSystemInfo
import kotlinx.coroutines.launch

// TODO: @ExperimentalFoundationApi just for Text(.combinedClickable) may go away
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
fun NavGraphBuilder.addMainScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    var delHostIpAddress = HostIpAddress("")

    composable(Screen.MainScreen.route) {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showDelHostCompDialog = remember { mutableStateOf(false) }
        val showAddHostCompDialog = remember { mutableStateOf(false) }
        val hostIpAddressList: List<HostIpAddress> by pharmScanViewModel.hostIpAddress.observeAsState(pharmScanViewModel.getAllHostIpAddress())

//        if (hostIpAddressList.isNullOrEmpty()) {
//            // set to defaults
//            pharmScanViewModel.insertHostIpAddress(HostIpAddress("0"))
//        }

        if (showDelHostCompDialog.value) {
            DeleteHostComputerAlert(
                hostComp = delHostIpAddress.name!!,
                showDialog = showDelHostCompDialog.value,
                onDel = {
                    showDelHostCompDialog.value = false
                        pharmScanViewModel.deleteRowHostIpAddress(delHostIpAddress)
                },
                onCancel = {
                    showDelHostCompDialog.value = false
                }
            )
        }

        if (showAddHostCompDialog.value) {
            AddHostIpAddress(
                showDialog = showAddHostCompDialog.value,
                onAdd = {
                    showAddHostCompDialog.value = false
                        pharmScanViewModel.insertHostIpAddress(HostIpAddress(it))
                },
                onCancel = {
                    showAddHostCompDialog.value = false
                }
            )
        }

        // NOTE: Changes need to be made also in all screens with the scafford settings
        Scaffold(
            scaffoldState = scaffoldState,
            //drawerShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomEnd = 15.dp, bottomStart = 15.dp),
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

//                    Spacer(modifier = Modifier.height(height = 10.dp))
//
//                    Text(
//                        text = "View File Name",
//                        modifier = Modifier.clickable {
//                            coroutineScope.launch {
//                                scaffoldState.drawerState.close()
//                                navController.navigate(Screen.ViewColDataFNameScreen.route)
//                            }
//                        },
//                        style = MaterialTheme.typography.caption,
//                        color = MaterialTheme.colors.onBackground
//                    )

                    Spacer(modifier = Modifier.height(height = 10.dp))

                    Text(
                        text = "View Ndc Table",
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                                navController.navigate(Screen.ViewNdcScreen.route)
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
            },
            topBar = {
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
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("+") },
                    onClick = {
                        showAddHostCompDialog.value = true
                    }
                )
            },
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    //.background(Color.Yellow),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Select Host Ip Address",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onBackground
                    )
                }

                LazyColumn(
                    modifier = Modifier.padding(top = 30.dp, bottom = 70.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(hostIpAddressList.size) { index ->
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(MaterialTheme.colors.secondary)
                        )
                        //Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                                //.background(Color.Yellow),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = hostIpAddressList[index].name!!,
                                modifier = Modifier
                                    // TODO: @ExperimentalFoundationApi just for Text(.combinedClickable) may go away
                                    .combinedClickable(
                                        onClick = {
                                            // TODO: PhysInvUploadScreen is only called if successful
                                            // connection to network other wise error.
                                            // Need to add network logic call here
                                            // Note selected row is passed to screen
                                            val columnValue = mapOf("hostIpAddress" to hostIpAddressList[index].name!!)
                                            UpdateSystemInfo(pharmScanViewModel, columnValue)
                                            navController.navigate(Screen.PhysInvUploadScreen.withArgs(hostIpAddressList[index].name!!))
                                        },
                                        onLongClick = {
                                            delHostIpAddress = hostIpAddressList[index]
                                            showDelHostCompDialog.value = true
                                        }
                                    ),
                                // TODO: put commented out code back in if @ExperimentalFoundationApi for Text(.combinedClickable)
                                //  above is deprecated
//                                    .clickable {
//                                        coroutineScope.launch {
//                                            scaffoldState.snackbarHostState.showSnackbar(
//                                                "Item: $itemVal"
//                                            )
//                                        }
//                                    }
//                                    .pointerInput(Unit) {
//                                        detectTapGestures(
//                                            onLongPress = {
//                                                //kotlin.io.println("********* Long Press *********")
//                                                //navController.popBackStack(Screen.MainScreen.route, inclusive = false)
//                                                coroutineScope.launch {
//                                                    scaffoldState.snackbarHostState.showSnackbar(
//                                                        "Long Press $itemVal"
//                                                    )
//                                                }
//                                            }
//                                        )
//                                    },
                                style = MaterialTheme.typography.h4,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                }
            }
        )
    }
}