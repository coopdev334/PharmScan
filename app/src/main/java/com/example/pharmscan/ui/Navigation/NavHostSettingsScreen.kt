package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ui.Screen.Screen
import com.example.pharmscan.BuildConfig
import com.example.pharmscan.ViewModel.PharmScanViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.addSettingsScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    composable(Screen.SettingsScreen.route) {
        val coroutineScope = rememberCoroutineScope()
        Column(
                modifier = Modifier.padding(start = 10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
        ) {
            Row(
                    modifier = Modifier
                            .fillMaxWidth(),
                    //.background(Color.Yellow),
                    horizontalArrangement = Arrangement.Center
            ) {
            }
            Text(
                    text = "<-",
                    fontSize = 40.sp,
                    modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .padding(start = 20.dp)
                            .clickable {
                                navController.popBackStack()
                            },
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
            )
            Text(
                    text = "Settings",
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(height = 30.dp))
            Box(
                    Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                    modifier = Modifier.fillMaxWidth()
                            .clickable {
                            },
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                        text = "Network Id",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onBackground
                )
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                    Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                        text = "Manual Price",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onBackground
                )
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                    Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                        text = "Cost Limit",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onBackground
                )
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                    Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                        text = "Tag Changes",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onBackground
                )
                Text(
                        text = "(FileSend)",
                        style = TextStyle(
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.Light,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 15.sp
                                ),
                        color = MaterialTheme.colors.onBackground
                )
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Box(
                    Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colors.secondary)
            )
        }
    }
}