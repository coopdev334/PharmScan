package com.example.pharmscan.ui.Dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CancelCollDataRecord(
    collDataRec: String,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onCancelRecord: () -> Unit
) {

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            modifier = Modifier.size(200.dp, 200.dp),
            title = {
                Text(
                    text = "Cancel?",
                    style = MaterialTheme.typography.h6
                )
            },
            text = {
                Text(
                    text = collDataRec,
                    style = MaterialTheme.typography.h5
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
                                text = "Ignore",
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                        Button(
                            onClick = { onCancelRecord() }
                        ) {
                            Text(
                                text = " Cancel ",
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                    }
                }
            }
        )
    }
}
