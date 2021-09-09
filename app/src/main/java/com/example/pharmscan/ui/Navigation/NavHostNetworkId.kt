package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.example.pharmscan.ViewModel.PharmScanViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue


fun NavGraphBuilder.addNetIdScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    composable(Screen.NetIdScreen.route) {
        var text by remember {mutableStateOf("")}

        Column(
            modifier = Modifier.padding(start = 10.dp, end = 8.dp),
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
                text = "Network Id",
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
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Host Account",
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Host Password",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                //PriceEntryCheckbox()
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

//@Composable
//fun PriceEntryCheckbox(){
//    val checkedState = remember {  mutableStateOf(true)  }
//    Checkbox(
//        modifier = Modifier.size(width = 40.dp, height = 20.dp),
//        checked = checkedState.value,
//        onCheckedChange = { checkedState.value = it }
//    )
//}
//
//@Composable
//fun CostLimit() {
//    var value by remember { mutableStateOf(TextFieldValue("")) }
//    BasicTextField(
//        value = value,
//        onValueChange = { value = it },
//        decorationBox = { innerTextField ->
//            Box(
//                Modifier
//                    .border(border = BorderStroke(1.dp, Color.Black))
//                    .padding(2.dp)
//                    .size(width = 60.dp, height = 30.dp),
//                contentAlignment = Alignment.CenterStart
//            ) {
//                innerTextField()
//            }
//        },
//        textStyle = TextStyle(fontSize = 25.sp)
//    )
//}
//
//@Composable
//fun TagChanges() {
//    var value by remember { mutableStateOf(TextFieldValue("")) }
//    BasicTextField(
//        value = value,
//        onValueChange = { value = it },
//        decorationBox = { innerTextField ->
//            Box(
//                Modifier
//                    .border(border = BorderStroke(1.dp, Color.Black))
//                    .padding(2.dp)
//                    .size(width = 60.dp, height = 30.dp),
//                contentAlignment = Alignment.CenterStart
//            ) {
//                innerTextField()
//            }
//        },
//        textStyle = TextStyle(fontSize = 25.sp)
//    )
//}