package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Screen.*
import com.example.pharmscan.ui.Utility.UpdateSettings

@ExperimentalComposeUiApi
fun NavGraphBuilder.addNetIdScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    composable(route = Screen.NetIdScreen.route) {
        val settings = pharmScanViewModel.getSettingsRow()
        var hostacct: InputWrapper? by remember { mutableStateOf(InputWrapper(settings[0].hostServerPort!!, null)) }
        var hostpassword: InputWrapper? by remember { mutableStateOf(InputWrapper(settings[0].hostPassword!!, null)) }
        val acctFocusRequester = remember {FocusRequester()}

        DisposableEffect(Unit) {
            acctFocusRequester.requestFocus()
            onDispose { }
        }

        fun InputsValid (): Boolean {
            when {
                hostacct?.value.isNullOrEmpty() -> return false
                hostpassword?.value.isNullOrEmpty() -> return false
            }

            when {
                hostacct?.errorId != null -> return false
                hostpassword?.errorId != null -> return false
                else -> return true
            }
        }

        fun onHostacctEntered(input: String) {
            val errorId = InputValidator.getHostAcctErrorIdOrNull(input)
            hostacct = hostacct?.copy(value = input, errorId = errorId)
        }

        fun onHostpasswordEntered(input: String) {
            val errorId = InputValidator.getHostPasswordErrorIdOrNull(input)
            hostpassword = hostpassword?.copy(value = input, errorId = errorId)
        }

        fun onImeActionClick() {
            if (hostacct!!.errorId == null && hostpassword!!.errorId == null) {
                val columnValue = mapOf("hostServerPort" to hostacct!!.value, "hostPassword" to hostpassword!!.value)
                UpdateSettings(pharmScanViewModel, columnValue)
                navController.popBackStack()
            }
        }

        fun onOkClick() {
            val columnValue = mapOf("hostServerPort" to hostacct!!.value, "hostPassword" to hostpassword!!.value)
            UpdateSettings(pharmScanViewModel, columnValue)
            navController.popBackStack()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Network Id",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
            }
            Spacer(Modifier.height(10.dp))
            TextFieldWithMsg(
                modifier = Modifier
                    .focusRequester(acctFocusRequester),
                // .onFocusChanged {},
                enabled = true,
                label = "HostAccount",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                inputWrapper = hostacct!!,
                onValueChange = ::onHostacctEntered,
                onImeKeyAction = ::onImeActionClick,
                length = 8
            )
            Spacer(Modifier.height(10.dp))
            TextFieldWithMsg(
                enabled = true,
                label = "HostPassword",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                inputWrapper = hostpassword!!,
                onValueChange = ::onHostpasswordEntered,
                onImeKeyAction = ::onImeActionClick,
                length = 6
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                TextButton(
                    modifier = Modifier.size(width = 90.dp, height = 45.dp),
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.h5
                    )
                }
                Button(
                    modifier = Modifier.size(width = 90.dp, height = 45.dp),
                    onClick = ::onOkClick,
                    enabled = InputsValid(),
                ) {
                    Text(
                        text = " OK ",
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}
