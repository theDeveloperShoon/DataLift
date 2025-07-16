package com.datalift.designsystem.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.datalift.designsystem.icon.DataliftIcons

@Composable
fun PrivateTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hasError: Boolean = false,
    imeAction: ImeAction = ImeAction.Unspecified,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    placeholder: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
){
    var textVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        visualTransformation =
            if(textVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if(textVisible){
                DataliftIcons.EnableVisibility
            } else { DataliftIcons.DisableVisibility }

            IconButton (onClick = {textVisible = !textVisible}) {
                Icon(
                    imageVector = image,
                    contentDescription = "View text field"
                )
            }
        },
        isError = hasError,
        placeholder = placeholder,
        supportingText = supportingText,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
    )

}