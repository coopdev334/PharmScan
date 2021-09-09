package com.example.pharmscan.ui.Utility

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    clear: ClearText,
    hintLabel: String = "",
    modifier: Modifier = Modifier,
    doSearch: (String) -> Unit = {}
) {
    var text by remember {mutableStateOf("")}
    val keyboardController = LocalSoftwareKeyboardController.current
    //var keyBrdInput by remember {mutableStateOf(0)}
    val requester1 = FocusRequester()

    if (clear.clear) {
        text = ""
        clear.clear = false
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier) {
        TextField(modifier = modifier
            .onPreviewKeyEvent { KeyEvent ->
            if (KeyEvent.key.nativeKeyCode == 66) {
                keyboardController?.hide()
                doSearch(text)
                //keyBrdInput = KeyEvent.key.nativeKeyCode
                //showKyBrdInputDialog.value = true
                true
            }else{
                false
            }

            }
            .focusRequester(requester1)
            .focusable(),
            value = text,
            onValueChange = {
                if (hintLabel == "Ndc"){
                    text = ManageLength(it, 11)
                }else{
                    text = ManageLength(it, 4)
                }
            },
            label = {
                Text(text = hintLabel)
            },
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(50),
            textStyle = MaterialTheme.typography.h5,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    doSearch(text)
                }
            ),
            trailingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Localized description")
            },
        )
        LaunchedEffect(Unit) {requester1.requestFocus()}
    }
}

// data class used to pass changable parameter to Search function
data class ClearText(
    var clear: Boolean
)