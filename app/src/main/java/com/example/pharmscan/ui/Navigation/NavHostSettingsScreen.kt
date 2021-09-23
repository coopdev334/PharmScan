package com.example.pharmscan.ui.Navigation

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ui.Screen.Screen
import com.example.pharmscan.ViewModel.PharmScanViewModel
import androidx.compose.ui.graphics.Color
import com.example.pharmscan.Data.Tables.Settings
import com.example.pharmscan.PharmScanApplication
import com.example.pharmscan.ui.Screen.InputValidator
import com.example.pharmscan.ui.Utility.*
import kotlinx.coroutines.runBlocking

fun NavGraphBuilder.addSettingsScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    composable(Screen.SettingsScreen.route) {

        val settings: List<Settings> by pharmScanViewModel.settings.observeAsState(pharmScanViewModel.getSettingsRow())
        val settingsNotInitialized = remember { mutableStateOf(true) }

        if (settings.isNullOrEmpty() && settingsNotInitialized.value) {
            settingsNotInitialized.value = false
            UpdateSettings(pharmScanViewModel)
        }

        Column(
                modifier = Modifier.padding(start = 10.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

            }
            Text(
                text = "<-",
                fontSize = 40.sp,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "Settings",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(height = 30.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Screen.NetIdScreen.route)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Network Id",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Manual Price",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                PriceEntryCheckbox(pharmScanViewModel)
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Cost Limit",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                CostLimit(pharmScanViewModel)
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tag Changes",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = "(FileSend)",
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Italic,
                        fontSize = 15.sp
                    ),
                    color = MaterialTheme.colors.onBackground
                )
                TagChanges(pharmScanViewModel)
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Auto Load NdcFile",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                AutoLoadNdcCheckbox(pharmScanViewModel)
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
        }
    }
}

@Composable
fun PriceEntryCheckbox(pharmScanViewModel: PharmScanViewModel) {
    var checkedState by remember { mutableStateOf(false) }

    val settings = pharmScanViewModel.getSettingsRow()

    if (!settings.isNullOrEmpty()) {
        checkedState = pharmScanViewModel.getSettingsRow()[0].ManualPrice == "on"
    }

    Checkbox(
        modifier = Modifier.size(width = 40.dp, height = 20.dp),
        checked = checkedState,
        onCheckedChange = {
            if (it) {
                val columnValue = mapOf("ManualPrice" to "on")
                UpdateSettings(pharmScanViewModel, columnValue)
            }else{
                val columnValue = mapOf("ManualPrice" to "off")
                UpdateSettings(pharmScanViewModel, columnValue)
            }
            checkedState = it
        }
    )
}

@Composable
fun CostLimit(pharmScanViewModel: PharmScanViewModel) {
    var value by remember { mutableStateOf("0")}
    var invalid: Boolean by remember { mutableStateOf(false)}
    val settingsNotInitialized = remember { mutableStateOf(true) }
    val settings = pharmScanViewModel.getSettingsRow()

    if (!settings.isNullOrEmpty() && settingsNotInitialized.value) {
        settingsNotInitialized.value = false
        value = settings[0].CostLimit.toString()
    }

    if (invalid) {
        Column() {
            Text(fontStyle = FontStyle.Italic, text = "Invalid")
            Text(fontStyle = FontStyle.Italic, text = "Decimal")
            Text(fontStyle = FontStyle.Italic, text = "Number")
        }
    }else {
        if(value == "0.00") {
            Column() {
                Text(fontStyle = FontStyle.Italic, text = "CostLimit")
                Text(fontStyle = FontStyle.Italic, text = "Disabled")
                //Text(fontStyle = FontStyle.Italic, text = "Number")
            }
        }
    }

    BasicTextField(
        value = value!!,
        onValueChange = {
            value = ManageLength(it,7)

            if (!it.isNullOrEmpty() && is2DecNumber(it)) {
                val columnValue = mapOf("CostLimit" to it)
                UpdateSettings(pharmScanViewModel, columnValue)
                invalid = false
            }else {
                invalid = true
            }
        },
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .border(border = BorderStroke(1.dp, Color.Black))
                    .padding(2.dp)
                    .size(width = 100.dp, height = 30.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                innerTextField()
            }
        },
        textStyle = TextStyle(fontSize = 25.sp)
    )


}

@Composable
fun TagChanges(pharmScanViewModel: PharmScanViewModel) {
    var value by remember { mutableStateOf("0") }
    var invalid: Boolean by remember { mutableStateOf(false)}
    val settings = pharmScanViewModel.getSettingsRow()

    if (!settings.isNullOrEmpty()) {
        value = pharmScanViewModel.getSettingsRow()[0].FileSendTagChgs.toString()
    }

    if (invalid) {
        Column() {
            Text(fontStyle = FontStyle.Italic, text = "Invalid")
            Text(fontStyle = FontStyle.Italic, text = "Number")
        }
    }

    BasicTextField(
        value = value!!,
        onValueChange = {
            value = ManageLength(it,4)

            if (!it.isNullOrEmpty() && !isNotWholeNumber(it) && it != "0") {
                val columnValue = mapOf("FileSendTagChgs" to it)
                UpdateSettings(pharmScanViewModel, columnValue)
                invalid = false
            }else {
                invalid = true
            }
        },
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .border(border = BorderStroke(1.dp, Color.Black))
                    .padding(2.dp)
                    .size(width = 60.dp, height = 30.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                innerTextField()
            }
        },
        textStyle = TextStyle(fontSize = 25.sp)
    )
}

@Composable
fun AutoLoadNdcCheckbox(pharmScanViewModel: PharmScanViewModel) {
    var checkedState by remember { mutableStateOf(true) }
    val settings = pharmScanViewModel.getSettingsRow()

    if (!settings.isNullOrEmpty()) {
        checkedState = pharmScanViewModel.getSettingsRow()[0].AutoLoadNdcFile == "on"
    }

    Checkbox(
        modifier = Modifier.size(width = 40.dp, height = 20.dp),
        checked = checkedState,
        onCheckedChange = {
            if (it) {
                val columnValue = mapOf("AutoLoadNdcFile" to "on")
                UpdateSettings(pharmScanViewModel, columnValue)
            }else{
                val columnValue = mapOf("AutoLoadNdcFile" to "off")
                UpdateSettings(pharmScanViewModel, columnValue)
            }
            checkedState = it
        }
    )
}