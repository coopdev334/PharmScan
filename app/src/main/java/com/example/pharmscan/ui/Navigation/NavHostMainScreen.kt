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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.pharmscan.ui.theme.PharmScanTheme
import com.example.pharmscan.ui.theme.Typography
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
            drawerShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomEnd = 15.dp, bottomStart = 15.dp),
            drawerContent = {
                Text(
                    text = "Settings",
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                            navController.navigate(Screen.Settings.route) } // FUTURE MERGE CONFLICT HERE
                    },
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W300,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(height = 10.dp))

                Text(
                    text = "View Cancel",
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                            navController.navigate(Screen.ViewCancel.route) }
                    },
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W300,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black
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
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W300,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black
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
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W300,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black
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
            content = { innerPadding ->
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
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.Black
                    )
                }

                LazyColumn(
                    Modifier.padding(top = 30.dp, bottom = 70.dp),
                    //contentPadding = innerPadding,
                    //contentPadding = PaddingValues(horizontal = 16.dp, vertical = 50.dp),

                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(itemList.size) { index ->
                        val itemVal = itemList[index]
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                //.background(colors[it % colors.primary])
                                .background(Color.LightGray)
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
                                fontSize = 40.sp,
                                color = Color.Blue
                                //style = TextStyle(background = Color.LightGray, textAlign = TextAlign.Center)
                            )
                        }
                    }
                }
            }
        )
    }
}