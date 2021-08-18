package com.example.pharmscan.ui.Screen

sealed class Screen(val route: String) {
    object MainScreen : Screen("MainRoute")

    // Helper function to append variable number of arguments to route
    // Note this works for mandatory arguments only
    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it") // append each argument at end of route
            }
        }
    }
}
