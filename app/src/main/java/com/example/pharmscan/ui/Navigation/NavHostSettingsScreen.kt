package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
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
import com.example.pharmscan.Data.Tables.Settings
import com.example.pharmscan.ui.Utility.UpdateSettings
import kotlinx.coroutines.runBlocking


@ExperimentalComposeUiApi
fun NavGraphBuilder.addSettingsScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    composable(Screen.SettingsScreen.route) {
        var settings by remember {mutableStateOf(pharmScanViewModel.getSettingsRow())}

        // update settings when built-in back button is used
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            UpdateSettings(pharmScanViewModel, settings[0])
        }

        if (settings.isEmpty()){
            runBlocking {
                val job = pharmScanViewModel.insertSettings(Settings("","","","",""))
                job.join()
                settings = pharmScanViewModel.getSettingsRow()
            }

        }

        Column(
                modifier = Modifier.padding(start = 10.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
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
                        UpdateSettings(pharmScanViewModel, settings[0])
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
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Screen.NetIdScreen.route)
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Manual Price",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                if (settings[0].ManualPrice == "checked") {
                    settings[0].ManualPrice = PriceEntryCheckbox(true)
                }else {
                    settings[0].ManualPrice = PriceEntryCheckbox(false)
                }


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
                    text = "Cost Limit",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                settings[0].CostLimit = CostLimit(settings)
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
                settings[0].FileSendTagChgs = TagChanges(settings)
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

@Composable
fun PriceEntryCheckbox(checked: Boolean): String {
    var checkedState by remember { mutableStateOf(checked) }

    Checkbox(
        modifier = Modifier.size(width = 40.dp, height = 20.dp),
        checked = checkedState,
        onCheckedChange = { checkedState = it }
    )


    if (checkedState) {
        return "checked"
    }
    return "unchecked"
}

@Composable
fun CostLimit(settings: List<Settings>): String {
    var value by remember { mutableStateOf(TextFieldValue(settings[0].CostLimit!!)) }

    BasicTextField(
        value = value,
        onValueChange = { value = it },
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .border(border = BorderStroke(1.dp, Color.Black))
                    .padding(2.dp)
                    .size(width = 90.dp, height = 30.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                innerTextField()
            }
        },
        textStyle = TextStyle(fontSize = 25.sp)
    )

    return value.text
}

@ExperimentalComposeUiApi
@Composable
fun TagChanges(settings: List<Settings>): String{
    var value by remember { mutableStateOf(TextFieldValue(settings[0].FileSendTagChgs!!)) }

    BasicTextField(
        value = value,
        onValueChange = {
            value = it },
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .border(border = BorderStroke(1.dp, Color.Black))
                    .padding(2.dp)
                    .size(width = 60.dp, height = 30.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                innerTextField()
            }
        },
        textStyle = TextStyle(fontSize = 25.sp)
    )

    return value.text
}