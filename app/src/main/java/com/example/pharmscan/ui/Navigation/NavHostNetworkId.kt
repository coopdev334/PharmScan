package com.example.pharmscan.ui.Navigation

import android.util.Log
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
import com.example.pharmscan.Data.Tables.Settings
import com.example.pharmscan.ui.Utility.UpdateSettings
import kotlinx.coroutines.runBlocking


fun NavGraphBuilder.addNetIdScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    composable(Screen.NetIdScreen.route) {
        var settings by remember {mutableStateOf(pharmScanViewModel.getSettingsRow())}

        if (settings.isEmpty()){
            runBlocking {
                var job = pharmScanViewModel.insertSettings(Settings("","","","",""))
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
                            UpdateSettings(pharmScanViewModel, settings[0])
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
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Host Account",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.width(width = 10.dp))
                val stringLocalType = HostNameTextField(settings)
                settings[0].hostAcct = stringLocalType
                Log.println(Log.DEBUG, "TESTING", stringLocalType)
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
                Spacer(modifier = Modifier.width(width = 10.dp))
                settings[0].hostPassword = HostPasswordTextField(settings)
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

//
@Composable
fun HostNameTextField(settings: List<Settings>): String {
    var value by remember { mutableStateOf(TextFieldValue(settings[0].hostAcct!!)) }
    BasicTextField(
        value = value,
        onValueChange = { value = it },
        decorationBox = { innerTextField ->
            Box(
                    Modifier
                            .border(border = BorderStroke(1.dp, Color.Black))
                            .padding(2.dp)
                            .size(width = 490.dp, height = 30.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                innerTextField()
            }
        },
        textStyle = TextStyle(fontSize = 25.sp)
    )
    return value.text
}

@Composable
fun HostPasswordTextField(settings: List<Settings>): String {
    var value by remember { mutableStateOf(TextFieldValue(settings[0].hostPassword!!)) }
    BasicTextField(
        value = value,
        onValueChange = { value = it },
        decorationBox = { innerTextField ->
            Box(
                    Modifier
                            .border(border = BorderStroke(1.dp, Color.Black))
                            .padding(2.dp)
                            .size(width = 490.dp, height = 30.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                innerTextField()
            }
        },
        textStyle = TextStyle(fontSize = 25.sp)
    )
    return value.text
}