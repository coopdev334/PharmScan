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
import com.example.pharmscan.Data.Tables.PSNdc
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Utility.ClearText
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.addViewNdcScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {

    composable(Screen.ViewNdcScreen.route) {

        var psNdc: List<PSNdc> by remember {mutableStateOf(listOf())}
        var startIndex: Int by remember {mutableStateOf(0)}
        var hintLabel by remember {mutableStateOf("Ndc")}
        val listState = rememberLazyListState()
        var clearTxt by remember {mutableStateOf(ClearText(false))}
        val coroutineScope = rememberCoroutineScope()
        var btnSelectedNdc by remember {mutableStateOf(true)}
        var btnSelectedPrice by remember {mutableStateOf(false)}
        var btnSelectedPkSz by remember {mutableStateOf(false)}
        var textStyle = MaterialTheme.typography.subtitle1
        var ontextStyle = MaterialTheme.typography.subtitle2
        var firstEntry = true

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
                text = "Ndc Table Search",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(height = 5.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                OutlinedButton(
                    onClick = {
                        hintLabel = "Ndc"
                        clearTxt.clear = true
                        btnSelectedNdc = true
                        btnSelectedPrice = false
                        btnSelectedPkSz = false
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
                Spacer(modifier = Modifier.width(width = 10.dp))
                OutlinedButton(
                    onClick = {
                        hintLabel = "Price"
                        clearTxt.clear = true
                        btnSelectedNdc = false
                        btnSelectedPrice = true
                        btnSelectedPkSz = false
                    },
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.onBackground),
                    contentPadding = PaddingValues(horizontal = 25.dp)
                ) {
                    Text(
                        text = "Price",
                        fontSize = 20.sp,
                        style = if(btnSelectedPrice) ontextStyle else textStyle
                    )
                }
                Spacer(modifier = Modifier.width(width = 10.dp))
                OutlinedButton(
                    onClick = {
                        hintLabel = "PkSz"
                        clearTxt.clear = true
                        btnSelectedNdc = false
                        btnSelectedPrice = false
                        btnSelectedPkSz = true
                    },
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.onBackground),
                    contentPadding = PaddingValues(horizontal = 25.dp)
                ) {
                    Text(
                        text = "PkSz",
                        fontSize = 20.sp,
                        style = if(btnSelectedPkSz) ontextStyle else textStyle
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
                if (!searchText.isEmpty()) {
                    when (hintLabel) {
                        "Ndc" -> {
                            psNdc = pharmScanViewModel.getAllPSNdcOrderByNdc()
                            startIndex = psNdc.indexOfFirst { it.ndc == searchText }
                        }
                        "Price" -> {
                            psNdc = pharmScanViewModel.getAllPSNdcOrderByPrice()
                            startIndex = psNdc.indexOfFirst { it.price == searchText }
                        }
                        "PkSz" -> {
                            psNdc = pharmScanViewModel.getAllPSNdcOrderByPackSz()
                            startIndex = psNdc.indexOfFirst { it.packsz == searchText }
                        }
                    }
                } else {
                    psNdc = pharmScanViewModel.getAllPSNdc()
                    startIndex = 0
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

                    items(psNdc.size) { index ->
                        Box(
                            modifier = Modifier.background(Color.LightGray)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 5.dp),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = "Ndc: " + psNdc[index].ndc,
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
                                        text = "Price: " + psNdc[index].price,
                                        style = MaterialTheme.typography.h6,
                                        color = MaterialTheme.colors.onBackground,
                                        //modifier = Modifier.width(156.dp)
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
                                        text = "PackSz: " + psNdc[index].packsz,
                                        style = MaterialTheme.typography.h6,
                                        color = MaterialTheme.colors.onBackground,
                                        //modifier = Modifier.width(156.dp)
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
