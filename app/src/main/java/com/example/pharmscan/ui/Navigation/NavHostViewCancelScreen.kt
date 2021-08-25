package com.example.pharmscan.ui.Navigation


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ui.Screen.Screen
import com.example.pharmscan.ui.Utility.SearchBar
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import com.example.pharmscan.ui.Dialog.CancelCollDataRecord

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.addViewCancelScreen(navController: NavController) {
    var delHostCompName = ""

    composable(Screen.ViewCancel.route) {

        var hintLabel by remember {mutableStateOf("Rec#")}
        //var text by remember {mutableStateOf("")}
        val listState = rememberLazyListState()
        var clearTxt by remember {mutableStateOf(false)}
        val showCancelCollDataDialog = remember { mutableStateOf(false) }

        // Use for testing
        // TODO: Remove this hard coded string after database is created. Get and set to database for
        // host computer name list. Also need to set new added computer name in add dialog
        val itemList = listOf("ABC", "CDE", "FGH", "IJK", "LMN", "OPQ", "RST", "ABC", "CDE", "FGH", "IJK", "LMN", "OPQ", "RST")

        if (showCancelCollDataDialog.value) {
            CancelCollDataRecord(
                collDataRec = delHostCompName,
                showDialog = showCancelCollDataDialog.value,
                onDismiss = {showCancelCollDataDialog.value = false})
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "<-",
                fontSize = 40.sp,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 5.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(height = 5.dp))
            Text(
                // TODO: finish this screen
                text = "Collected Data File Search",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground
            )
            SearchBar(
                clear = clearTxt,
                hintLabel = hintLabel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 18.dp),
            ) {
                // TODO: add database search logic
            }

            clearTxt = false
            Spacer(
                modifier = Modifier
                    .height(height = 5.dp)
            )
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                OutlinedButton(
                    onClick = {
                        hintLabel = "Rec#"
                        clearTxt = true
                    },
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.onBackground),
                    contentPadding = PaddingValues(horizontal = 25.dp)
                ) {
                    Text(
                        text = "Rec#",
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.width(width = 10.dp))
                OutlinedButton(
                    onClick = {
                        hintLabel = "Tag"
                        clearTxt = true
                    },
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.onBackground),
                    contentPadding = PaddingValues(horizontal = 25.dp)
                ) {
                    Text(
                        text = "Tag",
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.width(width = 10.dp))
                OutlinedButton(
                    onClick = {
                        hintLabel = "Ndc"
                        clearTxt = true
                    },
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.onBackground),
                    contentPadding = PaddingValues(horizontal = 25.dp)
                ) {
                    Text(
                        text = "Ndc",
                        fontSize = 20.sp
                    )
                }
            }

            //Spacer(modifier = Modifier.height(5.dp))

            LazyColumn(
                modifier = Modifier.padding(top = 20.dp, bottom = 5.dp, start = 5.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                items(itemList.size) { index ->
                    Box(
                        modifier = Modifier
                            //.height(80.dp)
                            .clickable {
                                showCancelCollDataDialog.value = true
                                println("mike ${itemList[index]}")
                            }
                            .background(color = Color.LightGray)

                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 5.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Tag: " + itemList[index],
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground,
                                    modifier = Modifier.width(150.dp)
                                )
                                Text(
                                    text = "Qty: " + itemList[index],
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                .padding(start = 5.dp),
                                //.background(Color.),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Rec#: " + itemList[index],
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground,
                                    modifier = Modifier.width(150.dp)
                                )
                                Text(
                                    text = "Match: " + itemList[index],
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 5.dp),
                                //.background(Color.Yellow),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Price: " + itemList[index],
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground,
                                    modifier = Modifier.width(150.dp)
                                )
                                Text(
                                    text = "PkSz: " + itemList[index],
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 5.dp),
                                //.background(Color.Yellow),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Ndc: " + itemList[index],
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}