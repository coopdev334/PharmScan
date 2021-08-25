package com.example.pharmscan.ui.Utility

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    clear: Boolean = false,
    hintLabel: String = "",
    modifier: Modifier = Modifier,
    doSearch: (String) -> Unit = {}
) {
    var text by remember {mutableStateOf("")}
    val keyboardController = LocalSoftwareKeyboardController.current

    if (clear) {
        text = ""
    }

    Box(modifier = modifier) {
        TextField(
            value = text,
            onValueChange = {
                text = it
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
    }
}