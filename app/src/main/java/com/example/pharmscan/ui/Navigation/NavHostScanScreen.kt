package com.example.pharmscan.ui.Navigation

import android.content.Intent
import android.util.Log
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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.pharmscan.Data.ScanLiveData
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.Data.Tables.Settings
import com.example.pharmscan.Data.Tables.SystemInfo
import com.example.pharmscan.PharmScanApplication
import com.example.pharmscan.R
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
        var statusBarText by rememberSaveable { mutableStateOf(it.arguments!!.getString("statusBar")) }
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        var statusBarBkGrColor by rememberSaveable { mutableStateOf(it.arguments!!.getString("bkgrColor")) }
        val statusBarBkGrColorObj: Color
        val systemInfo: List<SystemInfo> by pharmScanViewModel.systemInfo.observeAsState(pharmScanViewModel.getSystemInfoRow())
        val settings: List<Settings> by pharmScanViewModel.settings.observeAsState(pharmScanViewModel.getSettingsRow())
        val scanData: ScanLiveData by pharmScanViewModel.scanLiveData.observeAsState(ScanLiveData("", ""))
        var previousStatusBarText: String? by rememberSaveable { mutableStateOf("")}
        var previousBarBkgrColor: String? by rememberSaveable {mutableStateOf("")}
        var keyBrdInput by remember {mutableStateOf(0)}
        val showKyBrdInputDialog = remember { mutableStateOf(false) }
        val chgTagEnabled = rememberSaveable { mutableStateOf(false) }
        //val holdEnabled = rememberSaveable { mutableStateOf(false) }
        val manPrcOn = remember { mutableStateOf(false) }
        val ndcLoading = remember { mutableStateOf(false) }
        val sysInfoNotInitialized = remember { mutableStateOf(true) }
        val settingsNotInitialized = remember { mutableStateOf(true) }
        val toastObj = remember { mutableStateOf(Toast(PharmScanApplication.context)) }

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
//        val holdOnButtonColors: ButtonColors = buttonColors(
//            backgroundColor = Color.Cyan,
//            contentColor = Color.Black,
//            disabledBackgroundColor = Color.LightGray,
//            disabledContentColor = Color.Black
//        )
        var chgTagButtonColor by remember {mutableStateOf(defaultButtonColors)}
        //var holdButtonColor by remember {mutableStateOf(defaultButtonColors)}

        // Enable scanner on scan screen entry and disable on leaving scan screen
        val con = PharmScanApplication()
        val intent = Intent()
        intent.setAction("com.symbol.datawedge.api.ACTION")
        intent.putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "ENABLE_PLUGIN")
        con.getAppContext()?.sendBroadcast(intent)

        DisposableEffect(Unit) {

            onDispose {
                val psApp = PharmScanApplication()
                val dwIntent = Intent()
                dwIntent.action = "com.symbol.datawedge.api.ACTION"
                dwIntent.putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "DISABLE_PLUGIN")
                psApp.getAppContext()?.sendBroadcast(dwIntent)
            }
        }

        when (statusBarBkGrColor) {
            "yellow" -> statusBarBkGrColorObj = Color.Yellow
            "green" -> statusBarBkGrColorObj = Color.Green
            "cyan" -> statusBarBkGrColorObj = Color.Cyan
            else -> statusBarBkGrColorObj = Color.White
        }

        if (systemInfo.isNullOrEmpty() && sysInfoNotInitialized.value) {
            // set to defaults
            sysInfoNotInitialized.value = false
            UpdateSystemInfo(pharmScanViewModel)
            toastObj.value.cancel()
            ToastDisplay("WARNING: SystemInfo Table Not Initialized", Toast.LENGTH_LONG)
        }

        if (settings.isNullOrEmpty() && settingsNotInitialized.value) {
            settingsNotInitialized.value = false
            UpdateSettings(pharmScanViewModel)
            ToastDisplay("WARNING: Settings Table is Empty", Toast.LENGTH_LONG)
        }else{
            if (!settings.isNullOrEmpty()) {
                manPrcOn.value = settings[0].ManualPrice == "on"

                // Check if settings table has ONLY default row meaning user never setup settings table
                if (settings[0].FileSendTagChgs == "0" || settings[0].hostServerPort == "0") {
                    toastObj.value = ToastDisplay("WARNING: Settings Not FULLY Setup", Toast.LENGTH_SHORT)!!
                }
            }
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
                            //holdEnabled.value = true
                            val sysInfoMap = mapOf("Tag" to tag)
                            UpdateSystemInfo(pharmScanViewModel, sysInfoMap)
                            statusBarBkGrColor = "green"
                            statusBarText = "*** Scan BarCode ***"
                            chgTagButtonColor = defaultButtonColors
                            val sysInfo = pharmScanViewModel.getSystemInfoRow()

                            if (sysInfo[0].TotRecCount!!.toInt() > 0) {
                                val tagchgsLimit = pharmScanViewModel.getSettingsRow()[0].FileSendTagChgs!!.toInt()
                                val tagchgs = sysInfo[0].TagChangeCount!!.toInt()
                                if (tagchgs >= tagchgsLimit) {
                                    val sysinfoMap = mapOf("TotRecCount" to "0", "TagChangeCount" to "0")
                                    UpdateSystemInfo(pharmScanViewModel, sysinfoMap)
                                    Log.d("coop", "Tag change exceeded. Reset")
                                    pharmScanViewModel.uploadCollectedData()
                                    //navController.navigate(Screen.NoNetworkWarningScreen.route)
                                }
                            }else {
                                val sysinfoMap = mapOf("TotRecCount" to "0", "TagChangeCount" to "0")
                                UpdateSystemInfo(pharmScanViewModel, sysinfoMap)
                                ToastDisplay("Collected Data Table Empty", Toast.LENGTH_SHORT)
                            }
                        },
                        onCancel = {
                            showKyBrdInputDialog.value = false
                        }
                    )
                }

                // Currently Hold option is not used for pharmacy
                // Hold button is now the quit button. Leave code
                // here if Hold is ever used for pharmacy scans
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
                            NdcSearch(navController, ndc, pharmScanViewModel, "K")
                        },
                        onCancel = {
                            showKyBrdInputDialog.value = false
                        }
                    )
                }
            }
        }

        // Check if barcode data came from scanner not kybrd
        // If data found in ScanLiveData object do scanner processing for tag or ndc
        if (!scanData.barcodeData.isNullOrEmpty()) {
            //ToastDisplay("${scanData.barcodeType} ${scanData.barcodeData}", Toast.LENGTH_LONG)
            // after getting data clear out scan data object
            val barcode = scanData.barcodeData
            val type = scanData.barcodeType
            pharmScanViewModel.scanLiveData.value = ScanLiveData("", "")

            when (statusBarText) {
                "*** Scan Tag ***" -> {
                    if (type == "LABEL-TYPE-CODE128") {
                        chgTagEnabled.value = true
                        //holdEnabled.value = true
                        val sysInfoMap: Map<String, String>
                        if (barcode.isNullOrEmpty()){
                            toastObj.value.cancel()
                            ToastDisplay("Error! Empty/Null barcode data returned}", Toast.LENGTH_LONG)
                        }else {
                            if (barcode.length == 4) {
                                sysInfoMap = mapOf("Tag" to barcode)
                                UpdateSystemInfo(pharmScanViewModel, sysInfoMap)
                                statusBarBkGrColor = "green"
                                statusBarText = "*** Scan BarCode ***"
                                chgTagButtonColor = defaultButtonColors
                                val sysInfo = pharmScanViewModel.getSystemInfoRow()

                                if (sysInfo[0].TotRecCount!!.toInt() > 0) {
                                    val tagchgsLimit = pharmScanViewModel.getSettingsRow()[0].FileSendTagChgs!!.toInt()
                                    val tagchgs = sysInfo[0].TagChangeCount!!.toInt()
                                    if (tagchgs >= tagchgsLimit) {
                                        val sysinfoMap = mapOf("TotRecCount" to "0", "TagChangeCount" to "0")
                                        UpdateSystemInfo(pharmScanViewModel, sysinfoMap)
                                        Log.d("coop", "Tag change exceeded. Reset")
                                        pharmScanViewModel.uploadCollectedData()
                                        //navController.navigate(Screen.NoNetworkWarningScreen.route)
                                    }
                                }else {
                                    val sysinfoMap = mapOf("TotRecCount" to "0", "TagChangeCount" to "0")
                                    UpdateSystemInfo(pharmScanViewModel, sysinfoMap)
                                    ToastDisplay("Collected Data Table Empty", Toast.LENGTH_SHORT)
                                }
                            }else {
                                toastObj.value.cancel()
                                ToastDisplay("Code128 Tag barcode invalid length}", Toast.LENGTH_LONG)
                            }
                        }
                    }else {
                        toastObj.value.cancel()
                        ToastDisplay("Invalid barcode Type for Tag: ${scanData.barcodeType}", Toast.LENGTH_LONG)
                    }
                }

                "*** Scan BarCode ***" -> {
                    if (type == "LABEL-TYPE-UPCA") {
                        if (barcode.isNullOrEmpty()){
                            toastObj.value.cancel()
                            ToastDisplay("Error! Empty/Null barcode data returned}", Toast.LENGTH_LONG)
                        }else {
                            if (barcode.length == 12) {
                                NdcSearch(navController, barcode.substring(0..10), pharmScanViewModel, "S")
                            } else {
                                toastObj.value.cancel()
                                ToastDisplay("Invalid barcode length for Ndc: ${barcode.length}", Toast.LENGTH_LONG)
                            }
                        }
                    }else {
                        toastObj.value.cancel()
                        ToastDisplay("Invalid barcode Type for Ndc: ${scanData.barcodeType}", Toast.LENGTH_LONG)
                    }
                }
                else -> {
                    toastObj.value.cancel()
                    ToastDisplay("In Hold mode. Turn off Hold mode to scan again", Toast.LENGTH_LONG)
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
                        text = "View Ndc Table",
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                scaffoldState.drawerState.close()
                                navController.navigate(Screen.ViewNdcScreen.route)
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
                                if (!systemInfo.isNullOrEmpty()) {
                                    if (systemInfo[0].NdcLoading == "on") {
                                        ToastDisplay("Downloading...", Toast.LENGTH_SHORT)
                                    }
                                }
                            }
                        ) {
                            if (!systemInfo.isNullOrEmpty()) {
                                if (systemInfo[0].NdcLoading == "on") {
                                    //Icon(Icons.Filled.AddCircle, contentDescription = "")
                                    Image(
                                        painter = painterResource(R.drawable.ic_baseline_download_for_offline_24),
                                        contentDescription = "content description",
                                        colorFilter = ColorFilter.tint(
                                            Color.Red,
                                            BlendMode.ColorDodge
                                        ),
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    ndcLoading.value = true
                                } else {
                                    if (ndcLoading.value && systemInfo[0].NdcLoading == "off") {
                                        ndcLoading.value = false
                                        ToastDisplay("Ndc Done Loading Db", Toast.LENGTH_SHORT)
                                    }
                                }
                            }
                        }
                        // Currently not using manual price icon
//                        IconToggleButton(
//                            checked = manPrcOn.value,
//                            onCheckedChange = {
//
//                                if (manPrcOn.value){
//                                    val colVal = mapOf("ManualPrice" to "off")
//                                    ToastDisplay("Manual Price OFF", Toast.LENGTH_SHORT)
//                                    UpdateSettings(pharmScanViewModel, colVal)
//                                }else{
//                                    val colVal = mapOf("ManualPrice" to "on")
//                                    ToastDisplay("Manual Price ON", Toast.LENGTH_SHORT)
//                                    UpdateSettings(pharmScanViewModel, colVal)
//                                }
//                                manPrcOn.value = it
//                            }
//                        ) {
//                            val tint by animateColorAsState(
//                                if (manPrcOn.value) Color.Red
//                                else Color.White
//                            )
//                            Icon(
//                                imageVector = Icons.Filled.AddCircle,
//                                contentDescription = "Localized description",
//                                tint = tint
//                            )
//                        }
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
                            .size(width = 308.dp, height = 282.dp)
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

                                        var tag = "0"
                                        var fileSendTagChgs = "0"
                                        var tagChangeCount  = "0"

                                        if (!systemInfo.isNullOrEmpty()) {
                                            tag = systemInfo[0].Tag!!
                                            tagChangeCount = systemInfo[0].TagChangeCount!!
                                        }
                                        if (!settings.isNullOrEmpty()) {
                                            fileSendTagChgs = settings[0].FileSendTagChgs!!
                                        }
                                        Text(
                                            text = "Tag: $tag            $tagChangeCount/$fileSendTagChgs",
                                            style = MaterialTheme.typography.subtitle2,
                                            color = MaterialTheme.colors.onBackground
                                        )
                                    }
                                    var totQty = "0.0"
                                    var totAmt = "0.00"

                                    if (!systemInfo.isNullOrEmpty()) {
                                        totQty = systemInfo[0].TotQty!!
                                        totAmt = systemInfo[0].TotAmt!!
                                    }
                                    Spacer(modifier = Modifier.height(height = 6.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {


                                        Text(
                                            text = "Qty: " + totQty,
                                            style = MaterialTheme.typography.subtitle2,
                                            color = MaterialTheme.colors.onBackground
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(height = 6.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {

                                        Text(
                                            text = "Amount: " + totAmt,
                                            style = MaterialTheme.typography.subtitle2,
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
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        var totRecCnt = "0"

                                        if (!systemInfo.isNullOrEmpty()) {
                                            totRecCnt = systemInfo[0].TotRecCount!!
                                        }

                                        Text(
                                            text = "File Rec Count: " + totRecCnt,
                                            style = MaterialTheme.typography.body1,
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
                                            style = MaterialTheme.typography.body1,
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
                                            text = "Price:${collectedData[0].price!!.trimStart { it == '0' }}   PkSz:${collectedData[0].packsz!!.trimStart { it == '0' }}",
                                            style = MaterialTheme.typography.h5,
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
                                        //holdEnabled.value = true
                                    }else {
                                        previousStatusBarText = statusBarText
                                        previousBarBkgrColor = statusBarBkGrColor
                                        statusBarBkGrColor = "yellow"
                                        statusBarText = "*** Scan Tag ***"
                                        chgTagButtonColor = chgTagOnButtonColors
                                        //holdEnabled.value = false
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
                                //enabled = holdEnabled.value,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .size(width = 130.dp, height = 50.dp),
                                onClick = {
                                    navController.popBackStack()

                                    // Hold option is not used for pharmacy. Hold button is
                                    // changed to Quit button
//                                    if (statusBarText == "*** Hold ***") {
//                                        statusBarBkGrColor = "green"
//                                        statusBarText = "*** Scan BarCode ***"
//                                        holdButtonColor = defaultButtonColors
//                                        chgTagEnabled.value= true
//                                    } else {
//                                        val colDataRecCnt = pharmScanViewModel.getAllCollectedData()
//                                        if (colDataRecCnt.isNullOrEmpty()) {
//                                            ToastDisplay(
//                                                "Must have Last Scan for Hold",
//                                                Toast.LENGTH_LONG
//                                            )
//                                        } else {
//                                            previousStatusBarText = statusBarText
//                                            previousBarBkgrColor = statusBarBkGrColor
//                                            statusBarBkGrColor = "cyan"
//                                            statusBarText = "*** Hold ***"
//                                            holdButtonColor = holdOnButtonColors
//                                            chgTagEnabled.value = false
//                                        }
//                                    }
                                },
                                //colors = holdButtonColor
                                colors = buttonColors(backgroundColor = Color.Red)
                            ) {
                                //Text(text = "Hold")
                                Text(
                                    text = "Quit",
                                    color = Color.White
                                )
                            }
                        }
//                        Spacer(modifier = Modifier.height(height = 8.dp))
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceEvenly
//                        ){
//
//                            Button(
//                                modifier = Modifier
//                                    .clip(RoundedCornerShape(50.dp))
//                                    .size(width = 270.dp, height = 35.dp),
//                                colors = buttonColors(backgroundColor = Color.Red),
//                                onClick = {
//                                  navController.popBackStack()
//                            }
//                            ) {
//                                Text(
//                                    text = "Quit",
//                                    color = Color.White
//                                )
//
//                            }
//                        }
                    }

                }
            }
        )
        LaunchedEffect(Unit) {requester.requestFocus()}
    }
}
