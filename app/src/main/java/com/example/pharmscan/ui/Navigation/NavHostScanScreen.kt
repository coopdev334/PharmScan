package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.*
//import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ui.Screen.Screen
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import kotlinx.coroutines.Dispatchers
//import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

// TODO: @ExperimentalFoundationApi just for Text(.combinedClickable) may go away
@ExperimentalFoundationApi
fun NavGraphBuilder.addScanScreen(navController: NavController) {

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

        // Convert status bar string arg to Color object
        when (argStatusBarBkGrColor) {
            "yellow" -> statusBarBkgrColor = Color.Yellow
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
                            .background(MaterialTheme.colors.secondary)
                    ){
                        Column() {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Tag",
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
                                    text = "Qty: 345",
                                    style = MaterialTheme.typography.h5,
                                    color = MaterialTheme.colors.onBackground
                                )
                                //Spacer(modifier = Modifier.width(width = 10.dp))
                                Text(
                                    text = "Amount: 2345.33",
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
                            .background(MaterialTheme.colors.secondaryVariant)
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
                                    text = "Rec Count: 4345",
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
                            .background(MaterialTheme.colors.secondaryVariant)
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
                                statusBarBkgrColor = Color.Cyan
                                argTextstatusBar = "*** Hold ***"
                                // ChangeTagScan()
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
                                Text(text = "Quit")

                            }
                        }
                    }

                }
            }
        )
    }
}