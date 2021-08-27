package com.example.pharmscan.ui.Navigation

import android.widget.CheckBox
import android.widget.Toast
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
import androidx.compose.runtime.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
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
import com.example.pharmscan.ui.theme.PharmScanTheme
import com.example.pharmscan.ui.theme.Shapes
import com.example.pharmscan.ui.theme.Typography
import kotlinx.coroutines.launch

fun NavGraphBuilder.addNetFileSendScreen(navController: NavController) {
    composable(Screen.NetFileSend.route) {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        Spacer(modifier = Modifier.height(height = 5.dp))
        Column (
            modifier = Modifier.border(5.dp, MaterialTheme.colors.onBackground, Shapes.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "File Send",
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground)
            Spacer(modifier = Modifier.height(height = 5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                //.background(Color.Yellow),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Tag Changes",
                    modifier = Modifier
                        .padding(start = 20.dp),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.width(width = 20.dp))
                TagChangesTextField()
                Spacer(modifier = Modifier.width(width = 20.dp))
                TagCheckBox()
            }
            Spacer(modifier = Modifier.height(height = 5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                //.background(Color.Yellow),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Interval",
                    modifier = Modifier
                        .padding(start = 20.dp),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground
                )
                IntervalTextField()
                /* Text(
                    text = " secs",
                    modifier = Modifier
                        .padding(start = 20.dp),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground
                )*/
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                //.background(Color.Yellow),
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.height(height = 5.dp))
                Text(
                    text = "Record Count",
                    modifier = Modifier
                        .padding(start = 20.dp),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground
                )
                RecordCountTextField()
            }
        //Spacer(modifier = Modifier.height(height = 5.dp))
        //Text(text = "No Network Warning")
        //Spacer(modifier = Modifier.height(height = 5.dp))
        Box (
            Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(MaterialTheme.colors.onBackground)
        ) {}
            Text(text = "No Network Warning",
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground)
            Spacer(modifier = Modifier.height(height = 5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Tags",
                    modifier = Modifier
                        .padding(start = 20.dp),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground
                )
                TagTextField()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                //.background(Color.Yellow),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Interval",
                    modifier = Modifier
                        .padding(start = 20.dp),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground
                )
                IntervalTextFieldOther() // Change Name?
            }

            Spacer(modifier = Modifier.height(height = 5.dp))
            Button(onClick = {
                navController.popBackStack(Screen.Settings.route, inclusive = false)
            },
                modifier = Modifier.height(40.dp)
            ) {
                Text(
                    text = "OK",
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.body1
                )
            }
            Spacer(modifier = Modifier.height(height = 5.dp))
        }
    }
}
@Composable
fun TagCheckBox(){
    val checkedState = remember {  mutableStateOf(true)  }
    Checkbox(
        checked = checkedState.value,
        onCheckedChange = { checkedState.value = it },
        modifier = Modifier.padding(8.dp)
    )
}
@Composable
fun TagChangesTextField() {
    Column(Modifier.padding(16.dp)) {
        val textState = remember {  mutableStateOf(TextFieldValue())   }
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier
                .height(30.dp)
                .width(70.dp)
        )

    }
}
@Composable
fun IntervalTextField() {
    Column(Modifier.padding(16.dp)) {
        val textState = remember {  mutableStateOf(TextFieldValue())   }
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier
                .height(30.dp)
        )
    }
}
@Composable
fun RecordCountTextField() {
    Column(Modifier.padding(16.dp)) {
        val textState = remember {  mutableStateOf(TextFieldValue())   }
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier
                .height(30.dp)
        )
    }
}
@Composable
fun TagTextField() {
    Column(Modifier.padding(16.dp)) {
        val textState = remember {  mutableStateOf(TextFieldValue())   }
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier
                .height(30.dp)
        )
        //Text("The textfield's text")
    }
}
@Composable
fun IntervalTextFieldOther() {
    Column(Modifier.padding(16.dp)) {
        val textState = remember {  mutableStateOf(TextFieldValue())   }
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier
                .height(30.dp)
        )
    }
}