package com.example.pharmscan.ui.Dialog

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
fun HoldQtyKyBrdInput(
    kyBrdInput: Int,
    showDialog: Boolean,
    onAdd: (name: String) -> Unit,
    onCancel: () -> Unit
) {
    var text by remember { mutableStateOf(ConvertNumNativeKeyCodeToString(kyBrdInput)) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val requester = FocusRequester()
    var invalidInput = false
    var reformat = true

    if (showDialog) {
        AlertDialog(
            modifier = Modifier
                .size(240.dp, 230.dp)
                .onPreviewKeyEvent { KeyEvent ->
                    if (KeyEvent.key.nativeKeyCode == 66) {
                        if (text.isNotEmpty()) {
                            if (isDecNumber(text)) {
                                keyboardController?.hide()
                                onAdd(text)
                            } else {
                                invalidInput = true
                                text = ""
                            }
                        }
                        reformat = false
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
                            if (invalidInput || !reformat) {
                                text = ManageLength(it, 8)
                            }else{
                                if (it.length > 8 || it.isEmpty()) {
                                    reformat = false
                                }
                                text = ManageLength(ReformatText(it, 8), 8)
                            }
                        },
                        label = {
                            Column(
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text(
                                    text = "Qty",
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
                           text = if (invalidInput) "Requires number with 1 decimal" else "",
                           style = MaterialTheme.typography.h6,
                           color = textColor
                        )
                    }
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
                                if (!isDecNumber(text)){
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
        LaunchedEffect(Unit) {requester.requestFocus()}
    }
}
