package com.example.pharmscan.ui.Navigation

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ViewModel.PharmScanViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.pharmscan.Data.Tables.Settings
import com.example.pharmscan.PharmScanApplication
import com.example.pharmscan.R
import com.example.pharmscan.ViewModel.InsertNdc
import com.example.pharmscan.ui.Dialog.GetOpId
import com.example.pharmscan.ui.Dialog.SettingsPin
import com.example.pharmscan.ui.Screen.*
import com.example.pharmscan.ui.Utility.*
import kotlinx.coroutines.runBlocking

@ExperimentalComposeUiApi
fun NavGraphBuilder.addSettingsScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    composable(Screen.SettingsScreen.route) {

        val settings: List<Settings> by pharmScanViewModel.settings.observeAsState(pharmScanViewModel.getSettingsRow())
        val settingsNotInitialized = remember { mutableStateOf(true) }
        val showSettingsPinDialog = remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()

        if (settings.isNullOrEmpty() && settingsNotInitialized.value) {
            settingsNotInitialized.value = false
            UpdateSettings(pharmScanViewModel)
        }

        if (showSettingsPinDialog.value) {
            val settingsPin = stringResource(R.string.settings_pin)
            SettingsPin(
                showDialog = showSettingsPinDialog.value,
                onCancel = {
                    showSettingsPinDialog.value = false
                }
            ) { pin ->
                showSettingsPinDialog.value = false
                if (pin.trim() == settingsPin.trim())
                    navController.navigate(Screen.ResetDatabaseScreen.route)
            }
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
                    .padding(start = 10.dp)
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
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable {
//                            navController.navigate(Screen.NetIdScreen.route)
//                        },
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Network Id",
//                        style = MaterialTheme.typography.h5,
//                        color = MaterialTheme.colors.onBackground
//                    )
//                }
//                Spacer(modifier = Modifier.height(height = 10.dp))
//                Box(
//                    Modifier
//                        .fillMaxWidth()
//                        .height(1.dp)
//                        .background(MaterialTheme.colors.secondary)
//                )
//                Spacer(modifier = Modifier.height(height = 10.dp))
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
                    Spacer(modifier = Modifier.width(width = 60.dp))
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
                    Column() {
                        Row() {
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
                        }
                        Row() {
                            Text(
                                text = "Tag Changes",
                                style = MaterialTheme.typography.h5,
                                color = MaterialTheme.colors.onBackground
                            )

                        }
                    }
                    Spacer(modifier = Modifier.width(width = 30.dp))
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
                        text = "Host Server Port",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.width(width = 25.dp))
                    HostServerPort(pharmScanViewModel)
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
                Spacer(modifier = Modifier.height(height = 10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showSettingsPinDialog.value = true
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Reset Database",
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
            }
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
    var value by remember {mutableStateOf(InputWrapper("", null))}
    val settingsNotInitialized = remember { mutableStateOf(true) }
    val settings = pharmScanViewModel.getSettingsRow()

    fun InputsValid (): Boolean {
        when {
            value.value.isEmpty() -> return false
        }

        return when {
            value.errorId != null -> false
            else -> true
        }
    }

    fun onValueEntered(input: String) {
        val errorId = InputValidator.getCostLimitErrorIdOrNull(input)
        value = value.copy(value = input, errorId = errorId)

        if (InputsValid()) {
            val columnValue = mapOf("CostLimit" to input)
            UpdateSettings(pharmScanViewModel, columnValue)
        }
    }

    fun onImeActionClick() {
    }

    if (!settings.isNullOrEmpty() && settingsNotInitialized.value) {
        settingsNotInitialized.value = false
        value = value.copy(value = settings[0].CostLimit.toString(), errorId = null)
    }

    OutlinedTextFieldWithMsg(
        modifier = Modifier.onPreviewKeyEvent {KeyEvent -> KeyEvent.key.nativeKeyCode == 66},
        enabled = true,
        label = "",
        inputWrapper = value,
        onValueChange = ::onValueEntered,
        onImeKeyAction = ::onImeActionClick,
        length = 7
    )
}

@Composable
fun TagChanges(pharmScanViewModel: PharmScanViewModel) {
    var value by remember {mutableStateOf(InputWrapper("", null))}
    val settingsNotInitialized = remember { mutableStateOf(true) }
    val settings = pharmScanViewModel.getSettingsRow()

    fun InputsValid (): Boolean {
        when {
            value.value.isEmpty() -> return false
        }

        return when {
            value.errorId != null -> false
            else -> true
        }
    }

    fun onValueEntered(input: String) {
        val errorId = InputValidator.getTagChangesErrorIdOrNull(input)
        value = value.copy(value = input, errorId = errorId)

        if (InputsValid()) {
            val columnValue = mapOf("FileSendTagChgs" to input)
            UpdateSettings(pharmScanViewModel, columnValue)
        }
    }

    fun onImeActionClick() {
    }

    if (!settings.isNullOrEmpty() && settingsNotInitialized.value) {
        settingsNotInitialized.value = false
        value = value.copy(value = settings[0].FileSendTagChgs.toString(), errorId = null)
    }

    OutlinedTextFieldWithMsg(
        modifier = Modifier.onPreviewKeyEvent {KeyEvent -> KeyEvent.key.nativeKeyCode == 66},
        enabled = true,
        label = "",
        inputWrapper = value,
        onValueChange = ::onValueEntered,
        onImeKeyAction = ::onImeActionClick,
        length = 4
    )
}

@Composable
fun HostServerPort(pharmScanViewModel: PharmScanViewModel) {
    var value by remember {mutableStateOf(InputWrapper("", null))}
    val settingsNotInitialized = remember { mutableStateOf(true) }
    val settings = pharmScanViewModel.getSettingsRow()

    fun InputsValid (): Boolean {
        when {
            value.value.isEmpty() -> return false
        }

        return when {
            value.errorId != null -> false
            else -> true
        }
    }

    fun onValueEntered(input: String) {
        val errorId = InputValidator.getHostServerPortErrorIdOrNull(input)
        value = value.copy(value = input, errorId = errorId)

        if (InputsValid()) {
            val columnValue = mapOf("hostServerPort" to input)
            UpdateSettings(pharmScanViewModel, columnValue)
        }
    }

    fun onImeActionClick() {
    }

    if (!settings.isNullOrEmpty() && settingsNotInitialized.value) {
        settingsNotInitialized.value = false
        value = value.copy(value = settings[0].hostServerPort.toString(), errorId = null)
    }

    OutlinedTextFieldWithMsg(
        modifier = Modifier.onPreviewKeyEvent {KeyEvent -> KeyEvent.key.nativeKeyCode == 66},
        enabled = true,
        label = "",
        inputWrapper = value,
        onValueChange = ::onValueEntered,
        onImeKeyAction = ::onImeActionClick,
        length = 4
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
