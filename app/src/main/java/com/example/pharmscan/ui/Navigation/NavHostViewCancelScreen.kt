package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ui.Screen.Screen
import com.example.pharmscan.ui.theme.PharmScanTheme
import kotlinx.coroutines.launch

fun NavGraphBuilder.addViewCancelScreen(navController: NavController) {
    composable(Screen.ViewCancel.route) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
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
                modifier = Modifier.align(alignment = Alignment.Start).padding(start = 20.dp)
                    .clickable {
                        navController.popBackStack(Screen.MainScreen.route, inclusive = false)
                    },
                fontWeight = FontWeight.Light,
                //fontStyle = FontStyle.Italic,
                //color = Color.Black
            )
            Text(
                text = "PharmScan",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                //fontStyle = FontStyle.Italic,
                //color = Color.Black
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = "Version: 1.0",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = "Build: 1.0",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = "Unit Id: 0E89H67",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(height = 100.dp))
            Text(
                text = "By: coopdev334",
                fontSize = 20.sp
            )

        }
    }
}