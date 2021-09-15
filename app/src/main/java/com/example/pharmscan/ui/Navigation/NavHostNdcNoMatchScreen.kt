package com.example.pharmscan.ui.Navigation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.pharmscan.ViewModel.InsertNdc
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Screen.*
import com.example.pharmscan.ui.Utility.ToastDisplay
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

@ExperimentalComposeUiApi
fun NavGraphBuilder.addNdcNoMatchScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    composable(Screen.NdcNoMatchScreen.route) {

        val lifecycleOwner = LocalLifecycleOwner.current
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current
        val _events = Channel<ScreenEvent>()
        val eventFlow = _events.receiveAsFlow()
        val events = remember(eventFlow, lifecycleOwner) {
            eventFlow.flowWithLifecycle(
                lifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            )
        }

        var ndc by remember { mutableStateOf(InputWrapper("", null)) }
        var price by remember { mutableStateOf(InputWrapper("", null)) }
        var pksz by remember { mutableStateOf(InputWrapper("", null)) }
        var qty by remember { mutableStateOf(InputWrapper("", null)) }
        val ndcFocusRequester = remember { FocusRequester() }
        val pkszFocusRequester = remember { FocusRequester() }

        fun InputsValid (): Boolean {
            when {
                ndc.errorId != null -> return false
                price.errorId != null -> return false
                pksz.errorId != null -> return false
                qty.errorId != null -> return false
                else -> return true
            }
        }

        fun onNdcEntered(input: String) {
            val errorId = InputValidator.getNdcErrorIdOrNull(input)
            ndc = ndc.copy(value = input, errorId = errorId)
        }

        fun onPkSzEntered(input: String) {
            val errorId = InputValidator.getPkSzErrorIdOrNull(input)
            pksz = pksz.copy(value = input, errorId = errorId)
        }

        fun onPriceEntered(input: String) {
            val errorId = InputValidator.getPriceErrorIdOrNull(input)
            price = price.copy(value = input, errorId = errorId)
        }

        fun onQtyEntered(input: String) {
            val errorId = InputValidator.getQtyErrorIdOrNull(input)
            qty = qty.copy(value = input, errorId = errorId)
        }

        fun onImeActionClick() {
            CoroutineScope(Dispatchers.IO).launch {
                _events.send(ScreenEvent.MoveFocus())
            }
        }

        fun onImeActionDoneClick() {
            if (InputsValid()) {
                InsertNdc(pharmScanViewModel, ndc.value, price.value, pksz.value, qty.value, "N")
                navController.popBackStack()
            }
        }

        fun onOkClick() {
            InsertNdc(pharmScanViewModel, ndc.value, price.value, pksz.value, qty.value, "N")
            navController.popBackStack()
        }

        LaunchedEffect(Unit) {
            launch {
                events.collect { event ->
                    when (event) {
                        is ScreenEvent.ShowToast -> ToastDisplay(event.message, Toast.LENGTH_LONG)
                        is ScreenEvent.UpdateKeyboard -> {
                            if (event.show) keyboardController?.show() else keyboardController?.hide()
                        }
                        is ScreenEvent.ClearFocus -> focusManager.clearFocus()
                        is ScreenEvent.RequestFocus -> {
                            when (event.textFieldKey) {
                                FocusedTextFieldKey.NDC -> ndcFocusRequester.requestFocus()
                                FocusedTextFieldKey.PKSZ -> pkszFocusRequester.requestFocus()
                                else -> {}
                            }
                        }
                        is ScreenEvent.MoveFocus -> focusManager.moveFocus(event.direction)
                        is ScreenEvent.PopBackStack -> navController.popBackStack()
                    }
                }
            }
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
                    text = "Ndc No Match",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
            }
            TextFieldWithMsg(
                modifier = Modifier
                    .focusRequester(ndcFocusRequester)
                    .onFocusChanged {},
                enabled = true,
                label = "Ndc",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                inputWrapper = ndc,
                onValueChange = ::onNdcEntered,
                onImeKeyAction = ::onImeActionClick,
                length = 11
            )
            Spacer(Modifier.height(10.dp))
            TextFieldWithMsg(
                modifier = Modifier
                    .focusRequester(ndcFocusRequester)
                    .onFocusChanged {},
                enabled = true,
                label = "Price",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                //visualTransformation = ::NdcFilter,
                inputWrapper = price,
                onValueChange = ::onPriceEntered,
                onImeKeyAction = ::onImeActionClick,
                length = 8
            )
            Spacer(Modifier.height(10.dp))
            TextFieldWithMsg(
                modifier = Modifier
                    .focusRequester(pkszFocusRequester)
                    .onFocusChanged {},
                enabled = true,
                label = "PkSz",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                inputWrapper = pksz,
                onValueChange = ::onPkSzEntered,
                onImeKeyAction = ::onImeActionClick,
                length = 8
            )
            Spacer(Modifier.height(10.dp))
            TextFieldWithMsg(
                modifier = Modifier
                    .focusRequester(pkszFocusRequester)
                    .onFocusChanged {},
                enabled = true,
                label = "Qty",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                inputWrapper = qty,
                onValueChange = ::onQtyEntered,
                onImeKeyAction = ::onImeActionDoneClick,
                length = 6
            )
            Spacer(Modifier.height(8.dp))
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



