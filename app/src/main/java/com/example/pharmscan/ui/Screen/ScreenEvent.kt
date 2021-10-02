package com.example.pharmscan.ui.Screen

import androidx.compose.ui.focus.FocusDirection

sealed class ScreenEvent {
    class ShowToast(val message: String) : ScreenEvent()
    class UpdateKeyboard(val show: Boolean) : ScreenEvent()
    class RequestFocus(val textFieldKey: FocusedTextFieldKey) : ScreenEvent()
    object ClearFocus : ScreenEvent()
    class MoveFocus(val direction: FocusDirection = FocusDirection.Down) : ScreenEvent()
    object PopBackStack : ScreenEvent()
}