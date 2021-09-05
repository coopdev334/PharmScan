package com.example.pharmscan.ui.Dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun GetOpId(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onToScanScreen: (opid: String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            modifier = Modifier.size(200.dp, 200.dp),
            title = {
                Column(
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = "Enter",
                        style = MaterialTheme.typography.body1
                    )
                }
            },
            text = {
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = manageLength(it)
                    },
                    label = {
                        Column(
                            modifier = Modifier.padding(bottom = 20.dp)
                        ) {
                            Text(
                                text = "Operator Id",
                                style = MaterialTheme.typography.body1
                            )
                        }
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h6,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
    }
}

private fun manageLength(input: String) : String {
    var output: String

    if (input.toIntOrNull() == null) {
        return ""
    }
        if (input.length > 3) {
            output = input.substring(0..2)
        }else {
            output = input
        }

    return output
}

