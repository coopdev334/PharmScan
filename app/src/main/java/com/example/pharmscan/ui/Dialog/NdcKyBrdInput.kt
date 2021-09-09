package com.example.pharmscan.ui.Dialog

//import androidx.compose.foundation.focusable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.unit.dp
//import com.example.pharmscan.ui.Utility.*
//
//@Composable
//fun NdcKyBrdInput(
//    tagKyBrdInput: Int,
//    showDialog: Boolean,
//    onAdd: (name: String) -> Unit,
//    onCancel: () -> Unit
//) {
//    var text by remember { mutableStateOf(ConvertNumNativeKeyCodeToString(tagKyBrdInput)) }
//    val requester = FocusRequester()
//
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = {
//                onCancel()
//            },
//
//            modifier = Modifier.size(250.dp, 200.dp),
//            text = {
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                ){
//                    TextField(
//                        value = text,
//                        onValueChange = {
//
//                            text = ManageLength(ReformatText(it), 11)
//                        },
//                        label = {
//                            Column(
//                                modifier = Modifier.padding(bottom = 8.dp)
//                            ) {
//                                Text(
//                                    text = "Ndc",
//                                    style = MaterialTheme.typography.h5
//                                )
//                            }
//                        },
//                        singleLine = true,
//                        textStyle = MaterialTheme.typography.h5,
//                        modifier = Modifier
//                            .focusRequester(requester)
//                            .focusable()
//
//                    )
//                }
//            },
//            buttons = {
//                Column(
//                    horizontalAlignment = Alignment.End,
//                    modifier = Modifier
//                        .padding(all = 8.dp)
//                        .fillMaxWidth()
//                ) {
//                    Spacer(modifier = Modifier.height(35.dp))
//                    Row(
//                        verticalAlignment = Alignment.Bottom
//                    ) {
//                        TextButton(
//                            onClick = { onCancel() }
//                        ) {
//                            Text(
//                                text = "Cancel",
//                                style = MaterialTheme.typography.subtitle1
//                            )
//                        }
//                        Button(
//                            onClick = { onAdd(text) }
//                        ) {
//                            Text(
//                                text = " OK ",
//                                style = MaterialTheme.typography.subtitle1
//                            )
//                        }
//                    }
//                }
//            }
//        )
//        LaunchedEffect(Unit) {
//            requester.requestFocus()
//        }
//    }
//}


import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.pharmscan.ui.Utility.*

@ExperimentalComposeUiApi
@Composable
fun NdcKyBrdInput(
    kyBrdInput: Int,
    showDialog: Boolean,
    onAdd: (name: String) -> Unit,
    onCancel: () -> Unit
) {
    var text by remember { mutableStateOf(ConvertNumNativeKeyCodeToString(kyBrdInput)) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val requester = FocusRequester()
    var invalidLength = false
    var invalidNumeric = false
    var reformat = true

    if (showDialog) {
        AlertDialog(
            modifier = Modifier
                .size(240.dp, 230.dp)
                .onPreviewKeyEvent { KeyEvent ->
                    if (KeyEvent.key.nativeKeyCode == 66) {
                        if (text.isNotEmpty()) {
                            if (text.length < 11) {
                                invalidLength = true
                                text = ""
                            } else {
                                if (isWholeNumber(text)) {
                                    keyboardController?.hide()
                                    onAdd(text)
                                } else {
                                    invalidLength = false
                                    invalidNumeric = true
                                    text = ""
                                }
                            }
                        }
                        true
                    } else {
                        false
                    }
                },
            onDismissRequest = {
                onCancel()
            },
            buttons = {
                Column(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = text,
                        onValueChange = {
                            if (invalidLength || invalidNumeric || !reformat) {
                                text = ManageLength(it, 11)
                            }else{
                                if (it.length > 11 || it.isEmpty()) {
                                    reformat = false
                                }
                                text = ManageLength(ReformatText(it, 11), 11)
                            }
                        },
                        label = {
                            Column(
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text(
                                    text = "Ndc",
                                    style = MaterialTheme.typography.h5
                                )
                            }
                        },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .focusRequester(requester)
                            .focusable()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val textColor = if (invalidLength || invalidNumeric) {
                            MaterialTheme.colors.error
                        } else {
                            MaterialTheme.colors.onBackground
                        }

                        val infoText = if (invalidLength) {
                            "Requires 11 numbers"
                        }else{
                            if (invalidNumeric){
                                "Non Numeric Value"
                            }else{
                                ""
                            }
                        }
                        Text(
                            text = infoText,
                            style = MaterialTheme.typography.h6,
                            color = textColor
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        TextButton(
                            modifier = Modifier.size(width = 90.dp, height = 45.dp),
                            onClick = { onCancel() }
                        ) {
                            Text(
                                text = "Cancel",
                                style = MaterialTheme.typography.h5
                            )
                        }
                        Button(
                            modifier = Modifier.size(width = 90.dp, height = 45.dp),
                            onClick = {
                                text.trim()
                                if (text.length < 11){
                                    invalidLength = true
                                    text = ""
                                }else {
                                    if (isWholeNumber(text)){
                                        onAdd(text)
                                    }else {
                                        invalidLength = false
                                        invalidNumeric = true
                                        text = ""
                                    }
                                }
                            }
                        ) {
                            Text(
                                text = " OK ",
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                }
            }
        )
        LaunchedEffect(Unit) {requester.requestFocus()}
    }
}
