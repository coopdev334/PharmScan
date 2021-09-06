package com.example.pharmscan.ui.Dialog

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pharmscan.ui.Utility.*

@Composable
fun GetOpId(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onToScanScreen: (opid: String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    val requester = FocusRequester()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            modifier = Modifier
                .size(200.dp, 200.dp),
            text = {
                Text("")
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = ManageLength(it, 3)
                    },
                    label = {
                        Column(
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Text(
                                text = "Operator Id",
                                style = MaterialTheme.typography.h5
                            )
                        }
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h4,
                    modifier = Modifier
                        .focusRequester(requester)
                        .focusable()
                    //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            },
            buttons = {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(35.dp))
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        TextButton(
                            onClick = { onDismiss() }
                        ) {
                            Text(
                                text = "Cancel",
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                        Button(
                            onClick = {
                                if (text.isNotEmpty()) {
                                    onToScanScreen(text)
                                }else {
                                    onDismiss()
                                }
                            }
                        ) {
                            Text(
                                text = " OK ",
                                style = MaterialTheme.typography.subtitle1
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
