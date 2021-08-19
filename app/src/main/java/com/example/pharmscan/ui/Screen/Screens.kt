package com.example.pharmscan.ui.Screen

sealed class Screen(val route: String) {
    object MainScreen : Screen("MainRoute")
    object AboutScreen : Screen("AboutRoute")
    object ViewColDataFNameScreen : Screen("ViewColDataFNameRoute")
    object Settings : Screen("SettingsRoute")
    object NetFileSend : Screen("NetFileSendRoute")
    object NetID: Screen("NetIDRoute")
    object CostLimit : Screen("CostLimitRoute")
    object ViewCancel : Screen("ViewCancelRoute")
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
