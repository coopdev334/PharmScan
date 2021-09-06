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
import androidx.compose.ui.unit.dp
import com.example.pharmscan.ui.Utility.*

@Composable
fun TagKyBrdInput(
    tagKyBrdInput: Int,
    showDialog: Boolean,
    onAdd: (name: String) -> Unit,
    onCancel: () -> Unit
) {
    var text by remember { mutableStateOf(ConvertNumNativeKeyCodeToString(tagKyBrdInput)) }
    //text = ConvertNumNativeKeyCodeToString(tagKyBrdInput)
    val requester = FocusRequester()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onCancel()
            },

            modifier = Modifier.size(200.dp, 200.dp),
            text = {
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = ManageLength(ReformatText(it), 4)
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
                            onClick = { onCancel() }
                        ) {
                            Text(
                                text = "Cancel",
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                        Button(
                            onClick = { onAdd(text) }
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


