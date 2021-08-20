package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ui.Screen.Screen

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
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "TO DO: finish this screen",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(height = 10.dp))

        }
    }
}