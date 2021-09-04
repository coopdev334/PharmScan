package com.example.pharmscan.ui.Navigation


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
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
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Dialog.CancelCollDataRecord
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.addViewCancelScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    val delHostCompName = ""

        composable(Screen.ViewCancel.route) {

        var itemList: MutableList<CollectedData> by remember {mutableStateOf(mutableListOf<CollectedData>())}
        var startIndex: Int by remember {mutableStateOf(0)}
        var hintLabel by remember {mutableStateOf("Rec#")}
        val listState = rememberLazyListState()
        var clearTxt by remember {mutableStateOf(false)}
        val showCancelCollDataDialog = remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        var selectedIndex by remember{mutableStateOf(-1)}

        if (showCancelCollDataDialog.value) {
            CancelCollDataRecord(
                collDataRec = delHostCompName,
                showDialog = showCancelCollDataDialog.value,
                onDismiss = {
                    showCancelCollDataDialog.value = false
                    selectedIndex = -1
                },
                onCancelRecord = {
                    itemList[selectedIndex].qty = "000000"
                    pharmScanViewModel.insertCollectedData(itemList[selectedIndex])
                    showCancelCollDataDialog.value = false
                    selectedIndex = -1
                }
            )
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
                text = "Collected Data File Search",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground
            )

            clearTxt = false
            Spacer(modifier = Modifier.height(height = 5.dp))

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

            SearchBar(
                clear = clearTxt,
                hintLabel = hintLabel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 5.dp)
            ) {searchText ->
                // This lamda function gets called when soft keyboard popup search button is pressed
                if (hintLabel == "Rec#") {
                    itemList = pharmScanViewModel.getAllCollectedDataOrderByRecCnt()
                    startIndex = itemList.indexOfFirst { it.recount == searchText }
                }
                if (hintLabel == "Tag") {
                    itemList = pharmScanViewModel.getAllCollectedDataOrderByTag()
                    startIndex = itemList.indexOfFirst { it.loc == searchText }
                }
                if (hintLabel == "Ndc") {
                    itemList = pharmScanViewModel.getAllCollectedDataOrderByNdc()
                    startIndex = itemList.indexOfFirst { it.ndc == searchText }
                }
            }

            LazyColumn(
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 5.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // If startIndex is -1 then search did find a match. Clear itemlist
                if (startIndex == -1) {
                    itemList.clear()
                    itemList.add(CollectedData("","","","","","","", "", "", "","", "", "", "", ""))
                    startIndex = 0
                }

                items(itemList.size) { index ->
                    Box(
                        modifier = Modifier
                            .selectable(
                                selected = index == selectedIndex,
                                onClick = {
                                    if (selectedIndex != index)
                                        selectedIndex = index else selectedIndex = -1
                                }
                            )
                            .background(
                                if (itemList[index].qty == "000000")
                                Color.Red else Color.LightGray
                            )

                    ) {
                        if (selectedIndex == index) {
                            startIndex = selectedIndex
                            showCancelCollDataDialog.value = true
                        }

                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 5.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Tag: " + itemList[index].loc,
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground,
                                    modifier = Modifier.width(156.dp)
                                )
                                Text(
                                    text = "Qty: " + itemList[index].qty,
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 5.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Rec#: " + itemList[index].recount,
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground,
                                    modifier = Modifier.width(156.dp)
                                )
                                Text(
                                    text = "Match: " + itemList[index].matchflg,
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 5.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Price: " + itemList[index].price,
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground,
                                    modifier = Modifier.width(156.dp)
                                )
                                Text(
                                    text = "PkSz: " + itemList[index].packsz,
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 5.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Ndc: " + itemList[index].ndc,
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                        }
                    }

                    coroutineScope.launch {
                        listState.scrollToItem(index = startIndex)
                    }
                }
            }
        }
    }
}
