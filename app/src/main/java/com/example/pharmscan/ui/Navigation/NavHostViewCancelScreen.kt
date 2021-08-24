package com.example.pharmscan.ui.Navigation


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ui.Screen.Screen
import com.example.pharmscan.ui.Utility.SearchBar
import androidx.compose.ui.ExperimentalComposeUiApi

@ExperimentalComposeUiApi
fun NavGraphBuilder.addViewCancelScreen(navController: NavController) {

    composable(Screen.ViewCancel.route) {

        var hintLabel by remember {mutableStateOf("Rec#")}
        var text by remember {mutableStateOf("")}

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                //.background(Color.Yellow),
//                horizontalArrangement = Arrangement.Center
//            ) {
//            }
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
                // TODO: finish this screen
                text = "Collected Data Search",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(height = 5.dp))

            SearchBar(
                text1 = text,
                hintLabel = hintLabel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp),
            ) {
                // TODO: add database search logic
            }
            Spacer(modifier = Modifier.height(height = 5.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                OutlinedButton(onClick = {
                    hintLabel = "Rec#"
                    text = "" // TODO: not clearing search bar out when changing
                    },
                    shape = RoundedCornerShape(50),
                    border= BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor =  MaterialTheme.colors.onBackground),
                    contentPadding = PaddingValues(horizontal = 25.dp)
                ) {
                    Text(
                        text = "Rec#",
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.width(width = 10.dp))
                OutlinedButton(onClick = {
                    hintLabel = "Tag"
                    text = ""
                    },
                    shape = RoundedCornerShape(50),
                    border= BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor =  MaterialTheme.colors.onBackground),
                    contentPadding = PaddingValues(horizontal = 25.dp)
                ) {
                    Text(
                        text = "Tag",
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.width(width = 10.dp))
                OutlinedButton(onClick = {
                    hintLabel = "Ndc"
                    text = ""
                },
                    shape = RoundedCornerShape(50),
                    border= BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor =  MaterialTheme.colors.onBackground),
                    contentPadding = PaddingValues(horizontal = 25.dp)
                ) {
                    Text(
                        text = "Ndc",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}