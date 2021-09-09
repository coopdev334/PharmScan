package com.example.pharmscan.ui.Dialog

//import androidx.compose.foundation.layout.*
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.*
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun AddHostComputer(
//    showDialog: Boolean,
//    onAdd: (name: String) -> Unit,
//    onCancel: () -> Unit
//) {
//    var text by rememberSaveable { mutableStateOf("") }
//
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = {
//                onCancel()
//            },
//            modifier = Modifier.size(200.dp, 200.dp),
//            title = {
//                Column(
//                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
//                ) {
//                    Text(
//                        text = "Add",
//                        style = MaterialTheme.typography.body1
//                    )
//                }
//            },
//            text = {
//                OutlinedTextField(
//                    value = text,
//                    onValueChange = {
//                        text = it
//                    },
//                    label = {
//                        Column(
//                            modifier = Modifier.padding(bottom = 20.dp)
//                        ) {
//                            Text(
//                                text = "Host Computer",
//                                style = MaterialTheme.typography.body1
//                            )
//                        }
//                    },
//                    singleLine = true,
//                    textStyle = MaterialTheme.typography.h6
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
//                            onClick = {
//                                if (text.isNotEmpty()) {
//                                    onAdd(text)
//                                }
//                            }
//                        ) {
//                            Text(
//                                text = " Add ",
//                                style = MaterialTheme.typography.subtitle1
//                            )
//                        }
//                    }
//                }
//            }
//        )
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
fun AddHostComputer(
    showDialog: Boolean,
    onAdd: (name: String) -> Unit,
    onCancel: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    val requester = FocusRequester()

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
                            text = it
                        },
                        label = {
                            Column(
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text(
                                    text = "Host Computer",
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
                                if (text.isNotEmpty()) {
                                    onAdd(text)
                                }
                            }
                        ) {
                            Text(
                                text = " Add ",
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
