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
import com.example.pharmscan.Data.Tables.HostCompName
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Dialog.AddHostComputer
import com.example.pharmscan.ui.Dialog.DeleteHostComputerAlert
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// TODO: @ExperimentalFoundationApi just for Text(.combinedClickable) may go away
@ExperimentalFoundationApi
fun NavGraphBuilder.addMainScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    var delHostCompName: HostCompName = HostCompName("")

    composable(Screen.MainScreen.route) {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showDelHostCompDialog = remember { mutableStateOf(false) }
        val showAddHostCompDialog = remember { mutableStateOf(false) }
        val hostCompNameList: List<HostCompName> by pharmScanViewModel.hostCompName.observeAsState(listOf<HostCompName>())

        // Get all records from HostCompName table which will update livedata which will update
        // hostCompNameList which will cause a recompose of LazyColumn list screen
        pharmScanViewModel.updateHostCompNameLiveData()

        if (showDelHostCompDialog.value) {
            DeleteHostComputerAlert(
                hostComp = delHostCompName.name!!,
                showDialog = showDelHostCompDialog.value,
                onDel = {
                    showDelHostCompDialog.value = false
                    runBlocking {
                        val job = pharmScanViewModel.deleteHostCompName(delHostCompName)
                        // Wait for the insert coroutine to finish then update the livedata
                        job.join()
                        pharmScanViewModel.updateHostCompNameLiveData()
                    }
                },
                onCancel = {
                    showDelHostCompDialog.value = false
                }
            )
        }

        if (showAddHostCompDialog.value) {
            AddHostComputer(
                showDialog = showAddHostCompDialog.value,
                onAdd = {
                    showAddHostCompDialog.value = false
                    runBlocking {
                        val job = pharmScanViewModel.insertHostCompName(HostCompName(it))
                        // Wait for the insert coroutine to finish then update the livedata
                        job.join()
                        pharmScanViewModel.updateHostCompNameLiveData()
                    }
                },
                onCancel = {
                    showAddHostCompDialog.value = false
                }
            )
        }

        Scaffold(
            scaffoldState = scaffoldState,
            //drawerShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomEnd = 15.dp, bottomStart = 15.dp),
            drawerShape = MaterialTheme.shapes.large,
            drawerContent = {
                Text(
                    text = "Settings",
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                            scaffoldState.snackbarHostState.showSnackbar("Drawer Settings") }
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
                        text = "Select Host Computer",
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                                scaffoldState.snackbarHostState.showSnackbar("Drawer Settings") }
                        },
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onBackground
                    )
                }

                LazyColumn(
                    modifier = Modifier.padding(top = 30.dp, bottom = 70.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(hostCompNameList.size) { index ->
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
                                text = hostCompNameList[index].name!!,
                                modifier = Modifier
                                    // TODO: @ExperimentalFoundationApi just for Text(.combinedClickable) may go away
                                    .combinedClickable(
                                        onClick = {
                                            // TODO: PhysInvUploadScreen is only called if successful
                                            // connection to network other wise error.
                                            // Need to add network logic call here
                                            // Note selected row is passed to screen
                                            navController.navigate(Screen.PhysInvUploadScreen.withArgs(hostCompNameList[index].name!!))
                                        },
                                        onLongClick = {
                                            delHostCompName = hostCompNameList[index]
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