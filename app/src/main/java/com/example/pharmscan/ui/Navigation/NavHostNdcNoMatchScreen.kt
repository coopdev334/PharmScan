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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.pharmscan.Data.ScanLiveData
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
    composable(
        Screen.NdcNoMatchScreen.route + "/{ndcinputtype}",
        // Define argument list to pass to this composable in composable constructor
        // arguments parameter which is a list of navArguments.
        arguments = listOf(
            navArgument("ndcinputtype") {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {

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
        val ndcInputType = it.arguments?.getString("ndcinputtype")
        val ndcFocusRequester = remember { FocusRequester() }
        val showKyBrdInputDialog = remember { mutableStateOf(false) }
        val onClickEntered = remember { mutableStateOf(false) }
        //val pkszFocusRequester = remember { FocusRequester() }

        DisposableEffect(Unit) {
            ndcFocusRequester.requestFocus()
            onDispose { }
        }

        fun InputsValid (): Boolean {
            when {
                ndc.value.isEmpty() -> return false
                price.value.isEmpty() -> return false
                pksz.value.isEmpty() -> return false
                qty.value.isEmpty() -> return false
            }

            return when {
                ndc.errorId != null -> false
                price.errorId != null -> false
                pksz.errorId != null -> false
                qty.errorId != null -> false
                else -> true
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
                InsertNdc(pharmScanViewModel, ndc.value, price.value, pksz.value, qty.value, "R", ndcInputType!!)
                pharmScanViewModel.scanLiveData.value = ScanLiveData(null, null)
                navController.popBackStack()
            }
        }

        fun onOkClick() {
            onClickEntered.value = true
            InsertNdc(pharmScanViewModel, ndc.value, price.value, pksz.value, qty.value, "R", ndcInputType!!)
            pharmScanViewModel.scanLiveData.value = ScanLiveData(null, null)
            navController.popBackStack()
        }

        if (showKyBrdInputDialog.value) {
            showKyBrdInputDialog.value = false
            if (InputsValid() && !onClickEntered.value)
                onOkClick()
            else
                onClickEntered.value = false
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
                                //FocusedTextFieldKey.PKSZ -> pkszFocusRequester.requestFocus()
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
                    .onPreviewKeyEvent { KeyEvent ->
                        if (KeyEvent.key.nativeKeyCode == 66) {
                            showKyBrdInputDialog.value = true
                            true
                        }else
                            false
                    }
                    .focusRequester(ndcFocusRequester),
                   // .onFocusChanged {},
                enabled = true,
                label = "Ndc",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                inputWrapper = ndc,
                onValueChange = ::onNdcEntered,
                onImeKeyAction = ::onImeActionClick,
                length = 11,
                colors = TextFieldDefaults.textFieldColors(textColor = Color.Blue, focusedLabelColor = Color.Blue, unfocusedLabelColor = Color.Blue),
                textStyle = MaterialTheme.typography.h5

            )
            Spacer(Modifier.height(10.dp))
            TextFieldWithMsg(
                modifier = Modifier
                    .onPreviewKeyEvent { KeyEvent ->
                        if (KeyEvent.key.nativeKeyCode == 66) {
                            showKyBrdInputDialog.value = true
                            true
                        }else
                            false
                    },
//                modifier = Modifier
//                    .focusRequester(ndcFocusRequester)
//                    .onFocusChanged {},
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
                length = 8,
                colors = TextFieldDefaults.textFieldColors(textColor = Color.Blue, focusedLabelColor = Color.Blue, unfocusedLabelColor = Color.Blue),
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(Modifier.height(10.dp))
            TextFieldWithMsg(
                modifier = Modifier
                    .onPreviewKeyEvent { KeyEvent ->
                        if (KeyEvent.key.nativeKeyCode == 66) {
                            showKyBrdInputDialog.value = true
                            true
                        }else
                            false
                    },
//                modifier = Modifier
//                    .focusRequester(pkszFocusRequester)
//                    .onFocusChanged {},
                enabled = true,
                label = "PkSz",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                inputWrapper = pksz,
                onValueChange = ::onPkSzEntered,
                onImeKeyAction = ::onImeActionClick,
                length = 8,
                colors = TextFieldDefaults.textFieldColors(textColor = Color.Blue, focusedLabelColor = Color.Blue, unfocusedLabelColor = Color.Blue),
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(Modifier.height(10.dp))
            TextFieldWithMsg(
                modifier = Modifier
                    .onPreviewKeyEvent { KeyEvent ->
                        if (KeyEvent.key.nativeKeyCode == 66) {
                            showKyBrdInputDialog.value = true
                            true
                        }else
                            false
                    },
//                modifier = Modifier
//                    .focusRequester(pkszFocusRequester)
//                    .onFocusChanged {},
                enabled = true,
                label = "Qty",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                inputWrapper = qty,
                onValueChange = ::onQtyEntered,
                onImeKeyAction = ::onImeActionDoneClick,
                length = 6,
                colors = TextFieldDefaults.textFieldColors(textColor = Color.Blue, focusedLabelColor = Color.Blue, unfocusedLabelColor = Color.Blue),
                textStyle = MaterialTheme.typography.h5
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



