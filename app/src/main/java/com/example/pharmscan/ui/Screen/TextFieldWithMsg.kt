package com.example.pharmscan.ui.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TextFieldWithMsg(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    inputWrapper: InputWrapper,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: OnValueChange,
    onImeKeyAction: OnImeKeyAction,
    length: Int

) {
    var fieldValue = remember {
        mutableStateOf(TextFieldValue(inputWrapper.value, TextRange(inputWrapper.value.length)))
    }

    Column {
        TextField(
            modifier = modifier,
            enabled = enabled,
            value = fieldValue.value,
            onValueChange = {
                if (it.text.length <= length) fieldValue.value = it
                onValueChange(fieldValue.value.text)
            },
            label = {
                if (enabled)
                    Text(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        text =label
                    )else
                        Text(label)
            },
            isError = inputWrapper.errorId != null,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onAny = { onImeKeyAction() }),
        )
        if (inputWrapper.errorId != null) {
            Text(
                text = stringResource(inputWrapper.errorId),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}