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

fun NavGraphBuilder.addCostLimit(navController: NavController) {
    composable(Screen.CostLimit.route) {
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
                text = "Cost Limit",
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 100.dp), // MAYBE CHANGE THIS?
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
            CostLimitTextField()
            Button(onClick = {
                navController.popBackStack(Screen.Settings.route, inclusive = false)
            }) {
                Text(
                    text = "OK",
                    modifier = Modifier.padding(6.dp),
                    style = MaterialTheme.typography.h2
                )
            }
        }
        }
    }
