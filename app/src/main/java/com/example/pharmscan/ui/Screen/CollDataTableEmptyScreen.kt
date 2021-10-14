package com.example.pharmscan.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CollDataTableEmptyScreen(content: String) {

    // Handle backstack and do nothing for warning screen consuming the
    // backstack button so warning screen stays in foreground
//    BackHandler(){
//        Log.d("coop", "backstack")
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "!!! WARNING !!!",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(height = 10.dp))
        Text(
            text = "Collected Data Table",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground
        )
        Text(
            text = "is empty. No",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground
        )
        Text(
            text = "data was found.",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(height = 10.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground
        )
    }
}