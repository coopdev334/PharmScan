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
//fun TagKyBrdInput(
//    tagKyBrdInput: Int,
//    showDialog: Boolean,
//    onAdd: (name: String) -> Unit,
//    onCancel: () -> Unit
//) {
//    var text by remember { mutableStateOf(ConvertNumNativeKeyCodeToString(tagKyBrdInput)) }
//    val requester = FocusRequester()
//    var reformat by remember { mutableStateOf(true) }
//
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = {
//                onCancel()
//            },
//
//            modifier = Modifier.size(200.dp, 200.dp),
//            text = {
//                OutlinedTextField(
//                    value = text,
//                    onValueChange = {
//                        if (!reformat) {
//                            text = ManageLength(it, 4)
//                        }else{
//                            if (it.length > 4) {
//                                reformat = false
//                            }
//                            text = ManageLength(ReformatText(it, 4), 4)
//                        }
//                    },
//                    label = {
//                        Column(
//                            modifier = Modifier.padding(bottom = 8.dp)
//                        ) {
//                            Text(
//                                text = "Tag",
//                                style = MaterialTheme.typography.h5
//                            )
//                        }
//                    },
//                    singleLine = true,
//                    textStyle = MaterialTheme.typography.h4,
//                    modifier = Modifier
//                        .focusRequester(requester)
//                        .focusable()
//                )
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.example.pharmscan.ui.Utility.*

@Composable
fun TagKyBrdInput(
    kyBrdInput: Int,
    showDialog: Boolean,
    onAdd: (name: String) -> Unit,
    onCancel: () -> Unit
) {
    var text by remember { mutableStateOf(ConvertNumNativeKeyCodeToString(kyBrdInput)) }
    val requester = FocusRequester()
    var invalidInput = false
    var reformat = true

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onCancel()
            },
            modifier = Modifier.size(240.dp, 230.dp),
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
                            if (invalidInput || !reformat) {
                                text = ManageLength(it, 4)
                            }else{
                                if (it.length > 4) {
                                    reformat = false
                                }
                                text = ManageLength(ReformatText(it, 4), 4)
                            }
                        },
                        label = {
                            Column(
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text(
                                    text = "Tag",
                                    style = MaterialTheme.typography.h5
                                )
                            }
                        },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .focusRequester(requester)
                            .focusable()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val textColor = if (invalidInput) {
                            MaterialTheme.colors.error
                        } else {
                            MaterialTheme.colors.onBackground
                        }
                        Text(
                            text = if (invalidInput) "Non Numeric Value" else "",
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
                                if (!isNumber(text)){
                                    invalidInput = true
                                    text = ""
                                }else {
                                    onAdd(text)
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
        LaunchedEffect(Unit) {
            requester.requestFocus()
        }
    }
}



