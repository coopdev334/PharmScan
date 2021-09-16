package com.example.pharmscan.ui.Navigation

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.Data.Tables.Settings
import com.example.pharmscan.Data.Tables.SystemInfo
import com.example.pharmscan.ViewModel.NdcSearch
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ViewModel.ProcessHoldState
import com.example.pharmscan.ui.Dialog.HoldQtyKyBrdInput
import com.example.pharmscan.ui.Dialog.NdcKyBrdInput
import com.example.pharmscan.ui.Dialog.TagKyBrdInput
import com.example.pharmscan.ui.Utility.ToastDisplay
import com.example.pharmscan.ui.Utility.UpdateSettings
import com.example.pharmscan.ui.Utility.UpdateSystemInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        var statusBarText by rememberSaveable { mutableStateOf(it.arguments!!.getString("statusBar")) }
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        var statusBarBkGrColor by rememberSaveable { mutableStateOf(it.arguments!!.getString("bkgrColor")) }
        var statusBarBkGrColorObj = Color.White
        val systemInfo: List<SystemInfo> by pharmScanViewModel.systemInfo.observeAsState(pharmScanViewModel.getSystemInfoRow())
        val settings: List<Settings> by pharmScanViewModel.settings.observeAsState(pharmScanViewModel.getSettingsRow())

        //val settings: State<List<Settings>?> = pharmScanViewModel.settings.observeAsState()
        var previousStatusBarText: String? by rememberSaveable { mutableStateOf("")}
        var previousBarBkgrColor: String? by rememberSaveable {mutableStateOf("")}
        var keyBrdInput by remember {mutableStateOf(0)}
        val showKyBrdInputDialog = remember { mutableStateOf(false) }
        val chgTagEnabled = rememberSaveable { mutableStateOf(false) }
        val holdEnabled = rememberSaveable { mutableStateOf(false) }
        val manPrcOn = remember { mutableStateOf(false) }
        val defaultButtonColors: ButtonColors = buttonColors(
            backgroundColor = Color.Blue,
            contentColor = Color.White,
            disabledBackgroundColor = Color.LightGray,
            disabledContentColor = Color.Black
        )
        val chgTagOnButtonColors: ButtonColors = buttonColors(
            backgroundColor = Color.Yellow,
            contentColor = Color.Black,
            disabledBackgroundColor = Color.LightGray,
            disabledContentColor = Color.Black
        )
        val holdOnButtonColors: ButtonColors = buttonColors(
            backgroundColor = Color.Cyan,
            contentColor = Color.Black,
            disabledBackgroundColor = Color.LightGray,
            disabledContentColor = Color.Black
        )
        var chgTagButtonColor by remember {mutableStateOf(defaultButtonColors)}
        var holdButtonColor by remember {mutableStateOf(defaultButtonColors)}

        when (statusBarBkGrColor) {
            "yellow" -> statusBarBkGrColorObj = Color.Yellow
            "green" -> statusBarBkGrColorObj = Color.Green
            "cyan" -> statusBarBkGrColorObj = Color.Cyan
            else -> statusBarBkGrColorObj = Color.White
        }

        if (systemInfo.isNullOrEmpty()) {
            // set to defaults
            pharmScanViewModel.insertSystemInfo(SystemInfo("0", "0", "0", "0", "0", "0", "0"))
        }

        if (settings.isNullOrEmpty()) {
            // set to defaults
            pharmScanViewModel.insertSettings(Settings("0", "0", "0", "0", "0"))
        }else{
            manPrcOn.value = settings[0].ManualPrice == "on"
        }

        val requester = FocusRequester()

        // Key pressed, determine what context the key applies to. Capture first key
        // and open appropriate input dialog to get remaining input from user
        if (showKyBrdInputDialog.value) {
            when (statusBarText) {
                "*** Scan Tag ***" -> {
                    TagKyBrdInput(
                        keyBrdInput,
                        showDialog = showKyBrdInputDialog.value,
                        onAdd = { tag ->
                            showKyBrdInputDialog.value = false
                            chgTagEnabled.value = true
                            holdEnabled.value = true
                            val sysInfoMap = mapOf("Tag" to tag)
                            UpdateSystemInfo(pharmScanViewModel, sysInfoMap)
                            statusBarBkGrColor = "green"
                            statusBarText = "*** Scan BarCode ***"
                            chgTagButtonColor = defaultButtonColors

                            // TODO: temp for testing. Do warning check in network send code not here
                            val tagchgsLimit = pharmScanViewModel.getSettingsRow()[0].FileSendTagChgs!!.toInt()
                            val tagchgs = pharmScanViewModel.getSystemInfoRow()[0].TagChangeCount!!.toInt()
                             if (tagchgs >= tagchgsLimit) {
                                 navController.navigate(Screen.NoNetworkWarningScreen.route)
                             }
                        },
                        onCancel = {
                            showKyBrdInputDialog.value = false
                        }
                    )
                }

                "*** Hold ***" -> {
                    HoldQtyKyBrdInput(
                        keyBrdInput,
                        showDialog = showKyBrdInputDialog.value,
                        onAdd = {qty ->
                            showKyBrdInputDialog.value = false
                            ProcessHoldState(qty, pharmScanViewModel)
                        },
                        onCancel = {
                            showKyBrdInputDialog.value = false
                        }
                    )
                }

                else -> {
                    NdcKyBrdInput(
                        keyBrdInput,
                        showDialog = showKyBrdInputDialog.value,
                        onAdd = {ndc ->
                            showKyBrdInputDialog.value = false
                            NdcSearch(navController, ndc, pharmScanViewModel)
                        },
                        onCancel = {
                            showKyBrdInputDialog.value = false
                        }
                    )
                }
            }
        }


        // NOTE: Changes need to be made also in all screens with the scafford settings
        Scaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier
                .onPreviewKeyEvent { KeyEvent ->
                    if (KeyEvent.key.nativeKeyCode in 7..16) {
                        keyBrdInput = KeyEvent.key.nativeKeyCode
                        showKyBrdInputDialog.value = true
                    }
                    true
                }
                .focusRequester(requester)
                .focusable(),

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
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                ToastDisplay("clicked", Toast.LENGTH_SHORT)
                            }
                        ) {
                            Icon(Icons.Filled.Create, contentDescription = "")
                        }
                        IconToggleButton(
                            checked = manPrcOn.value,
                            onCheckedChange = {

                                if (manPrcOn.value){
                                    val colVal = mapOf("ManualPrice" to "off")
                                    ToastDisplay("Man Price Off", Toast.LENGTH_SHORT)
                                    UpdateSettings(pharmScanViewModel, colVal)
                                }else{
                                    val colVal = mapOf("ManualPrice" to "on")
                                    ToastDisplay("Man Price On", Toast.LENGTH_SHORT)
                                    UpdateSettings(pharmScanViewModel, colVal)
                                }
                                manPrcOn.value = it
                            }
                        ) {
                            val tint by animateColorAsState(
                                if (manPrcOn.value) Color.Red
                                else Color.White
                            )
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "Localized description",
                                tint = tint
                            )
                        }
                    }
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(50.dp))
                            .background(statusBarBkGrColorObj),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = statusBarText!!,
                            style = MaterialTheme.typography.h5,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.height(height = 8.dp))
                    Box(
                        modifier = Modifier
                            .size(width = 308.dp, height = 242.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color.LightGray)
                    ) {
                        Column() {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Column() {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Tag: ${systemInfo[0].Tag}           ${systemInfo[0].TagChangeCount}/${settings[0].FileSendTagChgs}",
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
                                            style = MaterialTheme.typography.h6,
                                            color = MaterialTheme.colors.onBackground
                                        )
                                        Text(
                                            text = "Amount: " + systemInfo[0].TotAmt,
                                            style = MaterialTheme.typography.h6,
                                            color = MaterialTheme.colors.onBackground
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(height = 8.dp))
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(start = 20.dp, end = 20.dp)
                                    ) {
                                        Box(
                                            Modifier
                                                .fillMaxWidth()
                                                .height(1.dp)
                                                .background(MaterialTheme.colors.onBackground)
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
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
                                    Spacer(modifier = Modifier.height(height = 8.dp))
                                    Row(
                                        Modifier.padding(start = 20.dp, end = 20.dp)
                                    ) {
                                        Box(
                                            Modifier
                                                .fillMaxWidth()
                                                .height(1.dp)
                                                .background(MaterialTheme.colors.onBackground)
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Column() {
                                    // Get last scanned row for display. If no rows default list
                                    var collectedData = pharmScanViewModel.getColDataLastInsertedRow()
                                    if (collectedData.isEmpty()) {
                                        collectedData = listOf(
                                            CollectedData(
                                                dept = "",
                                                prodcd = "",
                                                ndc = "",
                                                qty = "",
                                                price = "",
                                                packsz = "",
                                                xstock = "",
                                                matchflg = "",
                                                loc = "",
                                                operid = "",
                                                recount = "",
                                                date = "",
                                                seconds = "",
                                                itemtyp = "",
                                                itemcst = ""
                                            )
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Last Scan",
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
                                            text = "Ndc:${collectedData[0].ndc}  Qty:${collectedData[0].qty}",
                                            style = MaterialTheme.typography.h6,
                                            color = MaterialTheme.colors.onBackground
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Text(
                                            text = "Price:${collectedData[0].price}   PkSz:${collectedData[0].packsz}",
                                            style = MaterialTheme.typography.h6,
                                            color = MaterialTheme.colors.onBackground
                                        )
                                    }
                                }
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
                                enabled = chgTagEnabled.value,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .size(width = 130.dp, height = 50.dp),
                                onClick = {
                                    if (statusBarText == "*** Scan Tag ***") {
                                        statusBarText = previousStatusBarText
                                        statusBarBkGrColor = previousBarBkgrColor
                                        chgTagButtonColor = defaultButtonColors
                                    }else {
                                        previousStatusBarText = statusBarText
                                        previousBarBkgrColor = statusBarBkGrColor
                                        statusBarBkGrColor = "yellow"
                                        statusBarText = "*** Scan Tag ***"
                                        chgTagButtonColor = chgTagOnButtonColors
                                    }
                                    coroutineScope.launch(Dispatchers.Default) {
                                        // TODO: Implement Changetagscan function
                                        // supend fun ChangeTagScan()
                                    }
                                },
                                colors = chgTagButtonColor
                            ) {
                                Text(text = "Change Tag")
                            }
                            Button(
                                enabled = holdEnabled.value,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .size(width = 130.dp, height = 50.dp),
                                onClick = {

                                    if (statusBarText == "*** Hold ***") {
                                        statusBarBkGrColor = "green"
                                        statusBarText = "*** Scan BarCode ***"
                                        holdButtonColor = defaultButtonColors
                                    }else {
                                        previousStatusBarText = statusBarText
                                        previousBarBkgrColor = statusBarBkGrColor
                                        statusBarBkGrColor = "cyan"
                                        statusBarText = "*** Hold ***"
                                        holdButtonColor = holdOnButtonColors
                                    }
                                },
                                colors = holdButtonColor
                            ) {
                                Text(text = "Hold")
                            }
                        }
                        Spacer(modifier = Modifier.height(height = 8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){

                            Button(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .size(width = 270.dp, height = 35.dp),
                                colors = buttonColors(backgroundColor = Color.Red),
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
        LaunchedEffect(Unit) {requester.requestFocus()}
    }
}

