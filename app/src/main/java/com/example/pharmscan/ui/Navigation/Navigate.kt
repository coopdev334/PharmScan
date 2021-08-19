package com.example.pharmscan.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.pharmscan.ui.Screen.Screen

@Composable
fun Navigate() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route) {

        addMainScreen(navController = navController)
        addAboutScreen(navController = navController)
        addViewColDataFNameScreen(navController = navController)
        addSettingsScreen(navController = navController)
        addNetFileSendScreen(navController = navController)
        addNetID(navController = navController)
        addViewCancelScreen(navController = navController)
    }
}