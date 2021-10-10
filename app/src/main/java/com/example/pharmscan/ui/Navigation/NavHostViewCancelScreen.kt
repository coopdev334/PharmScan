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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Dialog.CancelCollDataRecord
import com.example.pharmscan.ui.Utility.ClearText
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.addViewCancelScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {

        composable(Screen.ViewCancel.route) {

        var collectedData: MutableList<CollectedData> by remember {mutableStateOf(mutableListOf<CollectedData>())}
        var startIndex: Int by remember {mutableStateOf(0)}
        var hintLabel by remember {mutableStateOf("Rec#")}
        val listState = rememberLazyListState()
        val clearTxt by remember {mutableStateOf(ClearText(false))}
        val showCancelCollDataDialog = remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        var selectedIndex by remember{mutableStateOf(-1)}
        var btnSelectedRec by remember {mutableStateOf(true)}
        var btnSelectedTag by remember {mutableStateOf(false)}
        var btnSelectedNdc by remember {mutableStateOf(false)}
        val textStyle = MaterialTheme.typography.subtitle1
        val ontextStyle = MaterialTheme.typography.subtitle2
        var firstEntry = true

        if (showCancelCollDataDialog.value) {
            CancelCollDataRecord(
                showDialog = showCancelCollDataDialog.value,
                onDismiss = {
                    showCancelCollDataDialog.value = false
                    selectedIndex = -1
                },
                onCancelRecord = {
                    collectedData[selectedIndex].qty = "000000"
                    pharmScanViewModel.insertCollectedData(collectedData[selectedIndex])
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
            Spacer(modifier = Modifier.height(height = 5.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                OutlinedButton(
                    onClick = {
                        hintLabel = "Rec#"
                        clearTxt.clear = true
                        btnSelectedRec = true
                        btnSelectedTag = false
                        btnSelectedNdc = false
                    },
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.onBackground),
                    contentPadding = PaddingValues(horizontal = 25.dp)
                ) {
                    Text(
                        text = "Rec#",
                        fontSize = 20.sp,
                        style = if(btnSelectedRec) ontextStyle else textStyle
                    )
                }
                Spacer(modifier = Modifier.width(width = 10.dp))
                OutlinedButton(
                    onClick = {
                        hintLabel = "Tag"
                        clearTxt.clear = true
                        btnSelectedRec = false
                        btnSelectedTag = true
                        btnSelectedNdc = false
                    },
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.onBackground),
                    contentPadding = PaddingValues(horizontal = 25.dp)
                ) {
                    Text(
                        text = "Tag",
                        fontSize = 20.sp,
                        style = if(btnSelectedTag) ontextStyle else textStyle
                    )
                }
                Spacer(modifier = Modifier.width(width = 10.dp))
                OutlinedButton(
                    onClick = {
                        hintLabel = "Ndc"
                        clearTxt.clear = true
                        btnSelectedRec = false
                        btnSelectedTag = false
                        btnSelectedNdc = true
                    },
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.onBackground),
                    contentPadding = PaddingValues(horizontal = 25.dp)
                ) {
                    Text(
                        text = "Ndc",
                        fontSize = 20.sp,
                        style = if(btnSelectedNdc) ontextStyle else textStyle
                    )
                }
            }

            SearchBar(
                clear = clearTxt,
                hintLabel = hintLabel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, top = 5.dp, end = 5.dp)
            ) { searchText ->
                // This lamda function gets called when searching
                when (hintLabel) {
                    "Rec#" -> {
                        collectedData = pharmScanViewModel.getAllCollectedDataOrderByRecCnt()
                        startIndex = collectedData.indexOfFirst { it.recount == searchText }
                    }
                    "Tag" -> {
                        collectedData = pharmScanViewModel.getAllCollectedDataOrderByTag()
                        startIndex = collectedData.indexOfFirst { it.loc == searchText }
                    }
                    "Ndc" -> {
                        collectedData = pharmScanViewModel.getAllCollectedDataOrderByNdc()
                        startIndex = collectedData.indexOfFirst { it.ndc == searchText }
                    }
                }
            }

            if (startIndex == -1) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "*** No Match ***",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.error
                )
            }
            else {
                LazyColumn(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 5.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    items(collectedData.size) { index ->
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
                                    if (collectedData[index].qty == "000000")
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
                                        text = "Tag: " + collectedData[index].loc,
                                        style = MaterialTheme.typography.h6,
                                        color = MaterialTheme.colors.onBackground,
                                        modifier = Modifier.width(156.dp)
                                    )
                                    Text(
                                        text = "Qty: " + collectedData[index].qty,
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
                                        text = "Rec#: " + collectedData[index].recount,
                                        style = MaterialTheme.typography.h6,
                                        color = MaterialTheme.colors.onBackground,
                                        modifier = Modifier.width(156.dp)
                                    )
                                    Text(
                                        text = "Match: " + collectedData[index].matchflg,
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
                                        text = "Price: " + collectedData[index].price,
                                        style = MaterialTheme.typography.h6,
                                        color = MaterialTheme.colors.onBackground,
                                        modifier = Modifier.width(156.dp)
                                    )
                                    Text(
                                        text = "PkSz: " + collectedData[index].packsz,
                                        style = MaterialTheme.typography.h6,
                                        color = MaterialTheme.colors.onBackground
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 5.dp),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Ndc: " + collectedData[index].ndc,
                                        style = MaterialTheme.typography.h6,
                                        color = MaterialTheme.colors.onBackground
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "InputType:  ",
                                        style = TextStyle(
                                                    fontFamily = FontFamily.Default,
                                                    fontWeight = FontWeight.W300,
                                                    fontStyle = FontStyle.Italic,
                                                    fontSize = 16.sp
                                                ),
                                        color = MaterialTheme.colors.onBackground
                                    )
                                    Text(
                                        text = collectedData[index].prodcd!!.first().toString(),
                                        style = MaterialTheme.typography.h5,
                                        color = MaterialTheme.colors.onBackground,
                                        fontStyle = FontStyle.Italic
                                    )
                                }
                            }
                        }

                        coroutineScope.launch {
                            if (firstEntry) {
                                listState.scrollToItem(index = startIndex)
                                firstEntry = false
                            }
                        }
                    }
                }
            }
        }
    }
}
