package com.example.pharmscan.ui.Navigation

import android.widget.CheckBox
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ui.Screen.Screen
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.theme.PharmScanTheme
import com.example.pharmscan.ui.theme.Typography
import kotlinx.coroutines.launch

fun NavGraphBuilder.addNdcMatchScreen(navController: NavController, pharmScanViewModel: PharmScanViewModel) {
    composable(Screen.NdcMatchScreen.route) {

        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        )
        {
            Row(
                    modifier = Modifier
                            .fillMaxWidth(),
                    //.background(Color.Yellow),
                    horizontalArrangement = Arrangement.Start
            ) {
                Text(
                        text = "NDC",
                        modifier = Modifier
                                .padding(start = 20.dp),
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onBackground
                )
                NDCTextField()
            }
            Row(
                    modifier = Modifier
                            .fillMaxWidth(),
                    //.background(Color.Yellow),
                    horizontalArrangement = Arrangement.Start
            ) {
                Text(
                        text = "PKS2",
                        modifier = Modifier
                                .padding(start = 20.dp),
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onBackground
                )
                PDS2TextField()
            }
            Row(
                    modifier = Modifier
                            .fillMaxWidth(),
                    //.background(Color.Yellow),
                    horizontalArrangement = Arrangement.Start
            ) {
                Text(
                        text = "Price",
                        modifier = Modifier
                                .padding(start = 20.dp),
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onBackground
                )
                PriceTextField()
            }
            Row(
                    modifier = Modifier
                            .fillMaxWidth(),
                    //.background(Color.Yellow),
                    horizontalArrangement = Arrangement.Start
            ) {
                Text(
                        text = "Enter Qty",
                        modifier = Modifier
                                .padding(start = 20.dp),
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onBackground
                )
                QtyTextField()
            }
            Row(
                    modifier = Modifier
                            .fillMaxWidth(),
                    //.background(Color.Yellow),
                    horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(height = 10.dp))
                Button(onClick = {
                    navController.popBackStack(Screen.ScanScreen.route, inclusive = false)
                }) {
                    Text(
                            text = "OK",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.h2
                    )
                }
                Spacer(modifier = Modifier.width(width = 30.dp))
                Button(onClick = {
                    navController.popBackStack(Screen.ScanScreen.route, inclusive = false)
                }) {
                    Text(
                            text = "CANCEL",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.h2
                    )
                }
            }
        }
    }
}
@Composable
fun NDCTextField() {
    Column(Modifier.padding(16.dp)) {
        val textState = remember { mutableStateOf(TextFieldValue()) }
        TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                readOnly = true,
                placeholder = { Text(text = "The textfield has this text.")}
        )
    }
}
@Composable
fun PDS2TextField() {
    Column(Modifier.padding(16.dp)) {
        val textState = remember { mutableStateOf(TextFieldValue()) }
        TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                readOnly = true,
                placeholder = { Text(text = "The textfield has this text.")}
        )
    }
}
@Composable
fun PriceTextField() {
    Column(Modifier.padding(16.dp)) {
        val textState = remember { mutableStateOf(TextFieldValue()) }
        TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                readOnly = true,
                placeholder = { Text(text = "The textfield has this text.")}

        )
    }
}
@Composable
fun QtyTextField() {
    Column(Modifier.padding(16.dp)) {
        val textState = remember { mutableStateOf(TextFieldValue()) }
        TextField(
                value = textState.value,
                onValueChange = { textState.value = it }
        )
    }
}