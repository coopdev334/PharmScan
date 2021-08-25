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
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

//    var isHintDisplayed by remember {
//        mutableStateOf(true)
//    }

    if (clear) {
        text = ""
    }

    Box(modifier = modifier) {
//        BasicTextField(
//            value = text,
//            onValueChange = {
//                text = it
//                onSearch(it)
//            },
//            maxLines = 1,
//            singleLine = true,
//            textStyle = MaterialTheme.typography.h5,
//            modifier = Modifier
//                .fillMaxWidth()
//                .shadow(5.dp, CircleShape)
//                .background(Color.White, CircleShape)
//                .padding(horizontal = 20.dp, vertical = 12.dp)
//                .onFocusChanged {
//                    isHintDisplayed = if (it.isFocused) false else true
//                },
//            keyboardOptions = KeyboardOptions(
//                imeAction = ImeAction.Search
//            )
//        )

        TextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
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
                    text = ""
                    // TODO: execute search of collect data table
                }
            ),
            trailingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Localized description")
            },
        )

//        if(isHintDisplayed) {
//            Text(
//                text = hint,
//                style = MaterialTheme.typography.h5,
//                color = MaterialTheme.colors.onBackground,
//                modifier = Modifier
//                    .padding(horizontal = 20.dp, vertical = 12.dp)
//            )
//        }
    }

}