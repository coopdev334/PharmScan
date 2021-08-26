package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.navigation.NavController
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.pharmscan.ui.theme.PharmScanTheme
import com.example.pharmscan.ui.theme.Typography
import kotlinx.coroutines.launch
// Notes: Trying before spacer style.
fun NavGraphBuilder.addSettingsScreen(navController: NavController) {
    composable(Screen.Settings.route) {
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
                horizontalArrangement = Arrangement.Center
            ) {
            }
            Text(
                text = "<-",
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp)
                    .clickable {
                        navController.popBackStack(Screen.MainScreen.route, inclusive = false)
                    },
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "Testing Match Screen",
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp)
                    .clickable {
                        navController.navigate(Screen.NavMatch.route)
                    },
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "Testing No Match Screen",
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp)
                    .clickable {
                        navController.navigate(Screen.NavNoMatch.route)
                    },
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "Network File Send",
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp)
                    .clickable {
                        navController.navigate(Screen.NetFileSend.route)
                    },
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
            // Before spacer
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 30.dp))

            Text(
                text = "Network ID",
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp)
                    .clickable {
                        navController.navigate(Screen.NetID.route)
                    },
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 30.dp))

            Text(
                text = "Cost Limit",
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp)
                    .clickable {
                        navController.navigate(Screen.CostLimit.route)
                    },
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.height(height = 30.dp))

            Text(
                text = "Manual Price Entry",
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
            PriceEntryCheckbox()
        }
    }
}
@Composable
fun PriceEntryCheckbox(){
    val checkedState = remember {  mutableStateOf(true)  }
    Checkbox(
        checked = checkedState.value,
        onCheckedChange = { checkedState.value = it }
    )
}


