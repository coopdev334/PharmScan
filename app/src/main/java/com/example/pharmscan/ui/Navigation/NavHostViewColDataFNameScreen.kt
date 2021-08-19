package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ui.Screen.Screen

fun NavGraphBuilder.addViewColDataFNameScreen(navController: NavController) {
    composable(Screen.ViewColDataFNameScreen.route) {
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
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp)
                    .clickable {
                        navController.popBackStack(Screen.MainScreen.route, inclusive = false)
                    },
                fontWeight = FontWeight.Light,
                //fontStyle = FontStyle.Italic,
                //color = Color.Black
            )
            Text(
                text = "Collected Data",
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium,
                //fontStyle = FontStyle.Italic,
                //color = Color.Black
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = "File Name",
                fontWeight = FontWeight.Medium,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(height = 30.dp))
            Box(
                Modifier
                    .height(45.dp)
                    .width(280.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "lkdsflkasdfl_12344.dat",
                    fontWeight = FontWeight.Medium,
                    fontSize = 25.sp
                )
            }
        }
    }
}