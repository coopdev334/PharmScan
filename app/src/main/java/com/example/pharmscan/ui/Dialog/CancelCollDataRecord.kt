package com.example.pharmscan.ui.Dialog

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

@ExperimentalComposeUiApi
@Composable
fun CancelCollDataRecord(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onCancelRecord: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val requester = FocusRequester()

    if (showDialog) {
        AlertDialog(
            modifier = Modifier
                .size(240.dp, 230.dp)
                .onPreviewKeyEvent { KeyEvent ->
                    if (KeyEvent.key.nativeKeyCode == 66) {
                        keyboardController?.hide()
                        onCancelRecord()
                        true
                    } else {
                        false
                    }
                }
                .focusRequester(requester)
                .focusable(),
            onDismissRequest = {
                onDismiss()
            },
            buttons = {
                Column(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Cancel?",
                        style = MaterialTheme.typography.h4
                    )
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        TextButton(
                            modifier = Modifier.size(width = 90.dp, height = 48.dp),
                            onClick = { onDismiss() }
                        ) {
                            Text(
                                text = "Ignore",
                                style = MaterialTheme.typography.h5
                            )
                        }
                        Button(
                            modifier = Modifier.size(width = 100.dp, height = 45.dp),
                            onClick = {
                                onCancelRecord()
                            }
                        ) {
                            Text(
                                text = " Cancel ",
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
