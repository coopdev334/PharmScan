package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

fun NavGraphBuilder.addMainScreen(navController: NavController) {
    composable(Screen.MainScreen.route) {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        // Use for testing
        val itemList = listOf("coopcomp1", "coopcomp2", "coopcomp3", "coopcomp4", "coopcomp2", "coopcomp3", "coopcomp4", "coopcomp2", "coopcomp3", "coopcomp4", "coopcomp1", "coopcomp2", "coopcomp3", "coopcomp4", "coopcomp2", "coopcomp3", "coopcomp4", "coopcomp2", "coopcomp3", "coopcomp4")

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
                        coroutineScope.launch { scaffoldState.snackbarHostState.showSnackbar("Snackbar") }
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
                    Modifier.padding(top = 30.dp, bottom = 70.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(itemList.size) { index ->
                        val itemVal = itemList[index]
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
                                text = itemList[index],
                                modifier = Modifier
                                    .clickable {
                                        coroutineScope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                "Item: $itemVal"
                                            )
                                        }
                                    }
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onLongPress = {
                                                //kotlin.io.println("********* Long Press *********")
                                                //navController.popBackStack(Screen.MainScreen.route, inclusive = false)
                                                coroutineScope.launch {
                                                    scaffoldState.snackbarHostState.showSnackbar(
                                                        "Long Press $itemVal"
                                                    )
                                                }
                                            }
                                        )
                                    },
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