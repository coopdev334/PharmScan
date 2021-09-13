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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.example.pharmscan.ViewModel.InsertNdc
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Screen.*
import com.example.pharmscan.ui.Utility.ToastDisplay
import com.example.pharmscan.ui.Utility.isDecNumber
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

@ExperimentalComposeUiApi
fun NavGraphBuilder.addNdcMatchScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    composable(
        route = Screen.NdcMatchScreen.route + "/{ndc}/{price}/{pksz}",
        // Define argument list to pass to this composable in composable constructor
        // arguments parameter which is a list of navArguments.
        arguments = listOf(
            navArgument("ndc") {
                type = NavType.StringType
                nullable = false
            },
            navArgument("price") {
                type = NavType.StringType
                nullable = false
            },
            navArgument("pksz") {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {

        //val context = LocalContext.current
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

        var ndc by remember { mutableStateOf(it.arguments?.getString("ndc")?.let { it1 -> InputWrapper(it1, null)}) }
        var pksz by remember { mutableStateOf(it.arguments?.getString("pksz")?.let { it1 -> InputWrapper(it1, null)}) }
        var price by remember { mutableStateOf(it.arguments?.getString("price")?.let { it1 -> InputWrapper(it1, null)}) }
        var qty by remember { mutableStateOf(InputWrapper("", null)) }
        val ndcFocusRequester = remember { FocusRequester() }
        val pkszFocusRequester = remember { FocusRequester() }
        val priceFocusRequester = remember { FocusRequester() }
        val qtyFocusRequester = remember { FocusRequester() }
        val areInputsValid = ndc?.errorId == null && pksz?.errorId == null && price?.errorId == null && qty.errorId == null
        var focusedTextField = FocusedTextFieldKey.NDC

        //***************************************
        fun onNdcEntered(input: String) {
            val errorId = InputValidator.getNdcErrorIdOrNull(input)
            ndc = ndc?.copy(value = input, errorId = errorId)
        }

        fun onPkSzEntered(input: String) {
            val errorId = InputValidator.getPkSzErrorIdOrNull(input)
            pksz = pksz?.copy(value = input, errorId = errorId)
        }

        fun onPriceEntered(input: String) {
            val errorId = InputValidator.getPriceErrorIdOrNull(input)
            price = price?.copy(value = input, errorId = errorId)
        }

        fun onQtyEntered(input: String) {
            val errorId = InputValidator.getQtyErrorIdOrNull(input)
            qty = qty.copy(value = input, errorId = errorId)
        }

        fun onTextFieldFocusChanged(key: FocusedTextFieldKey, isFocused: Boolean) {
            focusedTextField = if (isFocused) key else FocusedTextFieldKey.NONE
        }

        fun onNdcImeActionClick() {
            CoroutineScope(Dispatchers.IO).launch {
                _events.send(ScreenEvent.MoveFocus())
            }
        }

        suspend fun clearFocusAndHideKeyboard() {
            _events.send(ScreenEvent.ClearFocus)
            _events.send(ScreenEvent.UpdateKeyboard(false))
            focusedTextField = FocusedTextFieldKey.NONE
        }

        fun onOkClick() {
            CoroutineScope(Dispatchers.IO).launch {
                if (isDecNumber(price?.value)) {
                    if (isDecNumber(qty.value)) {
                        if (areInputsValid) {
                            clearFocusAndHideKeyboard()
                            _events.send(ScreenEvent.ShowToast("success"))
                            ndc?.let { it1 ->
                                pksz?.let { it2 ->
                                    price?.let { it3 ->
                                        InsertNdc(
                                            navController,
                                            pharmScanViewModel,
                                            it1.value,
                                            it2.value,
                                            it3.value,
                                            qty.value,
                                            "R"
                                        )
                                    }
                                }
                            }
                        } else {
                            _events.send(ScreenEvent.ShowToast("1 or more fields invalid"))
                        }
                    } else {
                        _events.send(ScreenEvent.ShowToast("Qty missing decimal pt"))
                    }
                } else {
                    _events.send(ScreenEvent.ShowToast("Price missing decimal pt"))
                }
                _events.send(ScreenEvent.PopBackStack)
            }

        }

        fun focusOnLastSelectedTextField() {
            CoroutineScope(Dispatchers.IO).launch {
                _events.send(ScreenEvent.RequestFocus(focusedTextField))
                delay(250)
                _events.send(ScreenEvent.UpdateKeyboard(true))
            }
        }
//************************************

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
                                FocusedTextFieldKey.Price -> ndcFocusRequester.requestFocus()
                                FocusedTextFieldKey.Qty -> pkszFocusRequester.requestFocus()
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
                    text = "Ndc Match",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
            }
            ndc?.let { it1 ->
                CustomTextField(
                    modifier = Modifier
                        .focusRequester(ndcFocusRequester)
                        .onFocusChanged { focusState ->
                            onTextFieldFocusChanged(
                                key = FocusedTextFieldKey.NDC,
                                isFocused = focusState.isFocused
                            )
                        },
                    enabled = false,
                    label = "Ndc",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    //visualTransformation = ::NdcFilter,
                    inputWrapper = it1,
                    onValueChange = ::onNdcEntered,
                    onImeKeyAction = ::onNdcImeActionClick,
                    length = 11
                )
            }
            Spacer(Modifier.height(10.dp))
            pksz?.let { it1 ->
                CustomTextField(
                    modifier = Modifier
                        .focusRequester(pkszFocusRequester)
                        .onFocusChanged { focusState ->
                            onTextFieldFocusChanged(
                                key = FocusedTextFieldKey.PKSZ,
                                isFocused = focusState.isFocused
                            )
                        },
                    enabled = false,
                    label = "PkSz",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    //visualTransformation = ::creditCardFilter,
                    inputWrapper = it1,
                    onValueChange = ::onPkSzEntered,
                    onImeKeyAction = ::onOkClick,
                    length = 8
                )
            }
            Spacer(Modifier.height(10.dp))
            price?.let { it1 ->
                CustomTextField(
                    modifier = Modifier
                        .focusRequester(ndcFocusRequester)
                        .onFocusChanged { focusState ->
                            onTextFieldFocusChanged(
                                key = FocusedTextFieldKey.Price,
                                isFocused = focusState.isFocused
                            )
                        },
                    enabled = false,
                    label = "Price",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    //visualTransformation = ::NdcFilter,
                    inputWrapper = it1,
                    onValueChange = ::onPriceEntered,
                    onImeKeyAction = ::onNdcImeActionClick,
                    length = 8
                )
            }
            Spacer(Modifier.height(10.dp))
            CustomTextField(
                modifier = Modifier
                    .focusRequester(pkszFocusRequester)
                    .onFocusChanged { focusState ->
                        onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.Qty,
                            isFocused = focusState.isFocused
                        )
                    },
                enabled = true,
                label = "Enter Qty",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                //visualTransformation = ::creditCardFilter,
                inputWrapper = qty,
                onValueChange = ::onQtyEntered,
                onImeKeyAction = ::onOkClick,
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
                    enabled = areInputsValid,
//                    onClick = {
//                        text.trim()
//                        if (text.length < 11){
//                            invalidLength = true
//                            text = ""
//                        }else {
//                            if (isNotWholeNumber(text)){
//                                onAdd(text)
//                            }else {
//                                invalidLength = false
//                                invalidNumeric = true
//                                text = ""
//                            }
//                        }
//                    }
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
