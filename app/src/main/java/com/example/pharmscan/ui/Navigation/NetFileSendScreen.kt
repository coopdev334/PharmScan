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
import com.example.pharmscan.ui.theme.PharmScanTheme
import com.example.pharmscan.ui.theme.Typography
import kotlinx.coroutines.launch

fun NavGraphBuilder.addNetFileSendScreen(navController: NavController) {
    composable(Screen.NetFileSend.route) {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row( //MAYBE I DONT NEED THIS
                modifier = Modifier
                    .fillMaxWidth(),
                //.background(Color.Yellow),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Tag",
                    modifier = Modifier
                        .padding(start = 20.dp),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground
                )
                TagCheckBox()
            }
            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = "Changes",
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )
            ChangesTextField()
            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = "Interval",
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )
            IntervalTextField()
            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = "Reccnt",
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )
            ReccntTextField()
            Spacer(modifier = Modifier.height(height = 10.dp))
            if (0==0){
                Text(
                   text = "")
            }
            else { // No Network exists
                Text(
                    text = "No Network Warning!",
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .padding(start = 20.dp),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                //.background(Color.Yellow),
                horizontalArrangement = Arrangement.Start
            ) {

                Text(
                    text = "Tag",
                    modifier = Modifier
                        .padding(start = 20.dp),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
                TagTextField()
                Text(
                    text = "Interval",
                    modifier = Modifier
                        .padding(start = 20.dp),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
                IntervalTextFieldOther() // Change Name?
            }

            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = "OK",
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 150.dp) // Change this value
                    .clickable {
                        navController.popBackStack(Screen.Settings.route, inclusive = false)
                    },
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}
@Composable
fun TagCheckBox(){
    val checkedState = remember {  mutableStateOf(true)  }
    Checkbox(
        checked = checkedState.value,
        onCheckedChange = { checkedState.value = it }
    )
}
@Composable
fun ChangesTextField() {
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
fun IntervalTextField() {
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
fun ReccntTextField() {
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
fun TagTextField() {
    Column(Modifier.padding(16.dp)) {
        val textState = remember {  mutableStateOf(TextFieldValue())   }
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier
                .height(30.dp)
                .width(30.dp)
        )
        //Text("The textfield's text")
    }
}
@Composable
fun IntervalTextFieldOther() { // MAYBE FIND A BETTER NAME
    Column(Modifier.padding(16.dp)) {
        val textState = remember {  mutableStateOf(TextFieldValue())   }
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier
                .height(30.dp)
                .width(30.dp)
        )
        //Text("The textfield's text")
    }
}
