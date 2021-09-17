package com.example.pharmscan.ui.Navigation

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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.example.pharmscan.ViewModel.InsertNdc
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Screen.*

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

        var ndc by remember { mutableStateOf(it.arguments?.getString("ndc")?.let { it1 -> InputWrapper(it1, null)}) }
        //var price by remember { mutableStateOf(it.arguments?.getString("price")?.let { it1 -> InputWrapper(it1, null)}) }
        var pksz by remember { mutableStateOf(it.arguments?.getString("pksz")?.let { it1 -> InputWrapper(it1, null)}) }
        var qty: InputWrapper? by remember { mutableStateOf(InputWrapper("", null)) }
        val manPrcOn = remember { mutableStateOf(false) }
        manPrcOn.value = pharmScanViewModel.getSettingsRow()[0].ManualPrice == "on"
        var price by remember { mutableStateOf(if (manPrcOn.value)InputWrapper("", null) else it.arguments?.getString("price")?.let { it1 -> InputWrapper(it1, null)}) }

        fun InputsValid (): Boolean {
            when {
                ndc?.value.isNullOrEmpty() -> return false
                price?.value.isNullOrEmpty() -> return false
                pksz?.value.isNullOrEmpty() -> return false
                qty?.value.isNullOrEmpty() -> return false
            }

            when {
                ndc?.errorId != null -> return false
                price?.errorId != null -> return false
                pksz?.errorId != null -> return false
                qty?.errorId != null -> return false
                else -> return true
            }
        }

        fun onQtyEntered(input: String) {
            val errorId = InputValidator.getQtyErrorIdOrNull(input)
            qty = qty?.copy(value = input, errorId = errorId)
        }

        fun onPriceEntered(input: String) {
            val errorId = InputValidator.getPriceErrorIdOrNull(input)
            price = price?.copy(value = input, errorId = errorId)
        }

        fun onImeActionClick() {
            if (qty!!.errorId == null && price!!.errorId == null) {
                InsertNdc(pharmScanViewModel, ndc!!.value, price!!.value, pksz!!.value, qty!!.value, "N")
                navController.popBackStack()
            }
        }

        fun onOkClick() {
            InsertNdc(pharmScanViewModel, ndc!!.value, price!!.value, pksz!!.value, qty!!.value, "N")
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
                    text = "Ndc Match",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
            }
            ndc?.let { it1 ->
                TextField(
                    enabled = false,
                    value = it1.value,
                    onValueChange = {},
                    label = { Text("Ndc") }
                )
            }
            Spacer(Modifier.height(10.dp))
            TextFieldWithMsg(
//                modifier = Modifier
//                    .focusRequester(ndcFocusRequester)
//                    .onFocusChanged {},
                enabled = manPrcOn.value,
                label = "Price",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                inputWrapper = price!!,
                onValueChange = ::onPriceEntered,
                onImeKeyAction = ::onImeActionClick,
                length = 8
            )
            Spacer(Modifier.height(10.dp))
            pksz?.let { it1 ->
                TextField(
                    enabled = false,
                    value = it1.value,
                    onValueChange = {},
                    label = { Text("PkSz") }
                )
            }
            Spacer(Modifier.height(10.dp))
            TextFieldWithMsg(
                enabled = true,
                label = "Qty",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                inputWrapper = qty!!,
                onValueChange = ::onQtyEntered,
                onImeKeyAction = ::onImeActionClick,
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
