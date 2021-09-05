package com.example.pharmscan.ui.Navigation

import android.view.KeyCharacterMap
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ui.Screen.Screen
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.example.pharmscan.Data.Tables.HostCompName
import com.example.pharmscan.Data.Tables.SystemInfo
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Dialog.TagKyBrdInput
import com.example.pharmscan.ui.Utility.UpdateSystemInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// TODO: @ExperimentalFoundationApi just for Text(.combinedClickable) may go away
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
fun NavGraphBuilder.addScanScreen(navController: NavController, pharmScanViewModel:PharmScanViewModel) {

    composable(
        route = Screen.ScanScreen.route + "/{statusBar}/{bkgrColor}",
        // Define argument list to pass to this composable in composable constructor
        // arguments parameter which is a list of navArguments.
        arguments = listOf(
            navArgument("statusBar") {
                type = NavType.StringType
                nullable = false
            },
            navArgument("bkgrColor") {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        // Get the arguments passed to this composable by key name
        // args must be present
        var argTextstatusBar by remember { mutableStateOf(it.arguments!!.getString("statusBar")) }
        val argStatusBarBkGrColor = it.arguments!!.getString("bkgrColor")
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        var statusBarBkgrColor by remember { mutableStateOf(Color.White) }
        val systemInfo: List<SystemInfo> by pharmScanViewModel.systemInfo.observeAsState(listOf<SystemInfo>())
        var currentStatusBar: String? by remember { mutableStateOf("")}
        var currentBarBkgrColor by remember {mutableStateOf(Color.White)}
        var tagKyBrdInput by remember {mutableStateOf(0)}
        val showTagKyBrdInputDialog = remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

        // Get record from SystemInfo table which will update livedata which will update
        // systemInfo which will cause a recompose of screen
        pharmScanViewModel.updateSystemInfoLiveData()

        // Convert status bar string arg to Color object
        when (argStatusBarBkGrColor) {
            "yellow" -> statusBarBkgrColor = Color.Yellow
        }
        val requester = FocusRequester()

        if (showTagKyBrdInputDialog.value) {
            focusManager.clearFocus()
            TagKyBrdInput(
                tagKyBrdInput,
                showDialog = showTagKyBrdInputDialog.value,
                onAdd = {tag ->
                    showTagKyBrdInputDialog.value = false
                    val columnValue = mapOf("Tag" to tag)
                    UpdateSystemInfo(pharmScanViewModel, columnValue)
                },
                onCancel = {
                    showTagKyBrdInputDialog.value = false
                }
            )
        }
        
        Scaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier
                .onPreviewKeyEvent { KeyEvent ->
                    println("coop ${KeyEvent.key.nativeKeyCode}")
                    if (KeyEvent.key.nativeKeyCode in 7..16) {
                        tagKyBrdInput = KeyEvent.key.nativeKeyCode
                        showTagKyBrdInputDialog.value = true
                    }
                    true
                }
                .focusRequester(requester)
                .focusable(),

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
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(statusBarBkgrColor),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = argTextstatusBar!!,
                            style = MaterialTheme.typography.h5,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.height(height = 8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = 80.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.LightGray)
                    ){
                        Column() {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Tag: " + systemInfo[0].Tag,
                                    style = MaterialTheme.typography.h5,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = "Qty: " + systemInfo[0].TotQty,
                                    style = MaterialTheme.typography.h5,
                                    color = MaterialTheme.colors.onBackground
                                )
                                Text(
                                    text = "Amount: " + systemInfo[0].TotAmt,
                                    style = MaterialTheme.typography.h5,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }

                        }

                    }
                    Spacer(modifier = Modifier.height(height = 8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = 80.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.LightGray)
                    ){
                        Column() {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "File",
                                    style = MaterialTheme.typography.h5,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = "Rec Count: " + systemInfo[0].TotRecCount,
                                    style = MaterialTheme.typography.h5,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(height = 8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = 80.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.LightGray)
                    ){
                        Column() {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "??????",
                                    style = MaterialTheme.typography.h5,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = "Something: xxxxxxxxxxx",
                                    style = MaterialTheme.typography.h5,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 4.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            Button(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .size(width = 110.dp, height = 50.dp),
                                onClick = {
                                statusBarBkgrColor = Color.Yellow
                                argTextstatusBar = "*** Scan Tag ***"
                                coroutineScope.launch(Dispatchers.Default) {
                                    // TODO: Implement Changetagscan function
                                    // supend fun ChangeTagScan()
                                }
                            }
                            ) {
                                Text(text = "Change Tag")

                            }
                            Button(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .size(width = 110.dp, height = 50.dp),
                                onClick = {

                                    if (argTextstatusBar == "*** Hold ***") {
                                        argTextstatusBar = currentStatusBar
                                        statusBarBkgrColor = currentBarBkgrColor
                                    }else {
                                        currentStatusBar = argTextstatusBar
                                        currentBarBkgrColor = statusBarBkgrColor
                                        statusBarBkgrColor = Color.Cyan
                                        argTextstatusBar = "*** Hold ***"
                                        focusManager.clearFocus()
                                    }
                                }
                            ) {
                                Text(text = "Hold")

                            }
                        }
                        Spacer(modifier = Modifier.height(height = 8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            // TODO: Button just for testing status bar color and text change
                            // use this to change info on this screen as user selects options
                            // and status or other displays.
                            Button(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .size(width = 250.dp, height = 50.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                                onClick = {
                                  navController.popBackStack()
                            }
                            ) {
                                Text(
                                    text = "Quit",
                                    color = Color.White
                                )

                            }
                        }
                    }

                }
            }
        )

        if (argTextstatusBar == "*** Scan Tag ***") {
            LaunchedEffect(Unit) {
                requester.requestFocus()
            }
        }
    }
}
