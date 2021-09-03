package com.example.pharmscan.ui.Navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ui.Screen.Screen

// TODO: @ExperimentalFoundationApi just for Text(.combinedClick) may go away
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun Navigate(pharmScanViewModel: PharmScanViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route) {

        addMainScreen(navController = navController, pharmScanViewModel)
        addAboutScreen(navController = navController)
        addViewColDataFNameScreen(navController = navController)
        addViewCancelScreen(navController = navController, pharmScanViewModel)
        addPhysInvUploadScreen(navController = navController)
        addSettingsScreen(navController = navController)
        addNetFileSendScreen(navController = navController)
        addNetID(navController = navController)
        addCostLimit(navController = navController)
        addNavMatchScreen(navController = navController)
        addNavNoMatchScreen(navController = navController)
        addScanScreen(navController = navController)
    }
}