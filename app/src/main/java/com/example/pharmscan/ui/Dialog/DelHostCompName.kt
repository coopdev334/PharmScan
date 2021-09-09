package com.example.pharmscan.ui.Dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DeleteHostComputerAlert(
    hostComp: String,
    showDialog: Boolean,
    onDel: () -> Unit,
    onCancel: () -> Unit
) {
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
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Cancel?",
                        style = MaterialTheme.typography.h4
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = hostComp,
                        style = MaterialTheme.typography.h4
                    )
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        TextButton(
                            modifier = Modifier.size(width = 90.dp, height = 48.dp),
                            onClick = { onCancel() }
                        ) {
                            Text(
                                text = "Cancel",
                                style = MaterialTheme.typography.h5
                            )
                        }
                        Button(
                            modifier = Modifier.size(width = 100.dp, height = 45.dp),
                            onClick = {
                                onDel()
                            }
                        ) {
                            Text(
                                text = " Delete ",
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                }
            }
        )
    }
}
