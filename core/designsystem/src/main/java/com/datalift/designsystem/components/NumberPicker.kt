package com.datalift.designsystem.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.datalift.designsystem.icon.DataliftIcons

@Composable
fun DataliftNumberPicker(
    modifier: Modifier = Modifier,
    value: Long,
    onValueChange: (Long) -> Unit,
    range: Pair<Long, Long> = Pair(1, Long.MAX_VALUE-1)
){
    var textValue by remember(value) { mutableStateOf(value.toString()) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                val newValue = (textValue.toLongOrNull() ?: range.first)
                    .minus(1)
                    .coerceAtLeast(range.first)
                textValue = newValue.toString()
                onValueChange(newValue)
            },
            enabled = (textValue.toLongOrNull() ?: range.first) > range.first
        ){
            Icon(
                imageVector = DataliftIcons.Decrease,
                contentDescription = "Decrease"
            )
        }

        TextField(
            value = textValue,
            onValueChange = { newValue ->
                textValue = newValue
                if (newValue.isBlank()  || newValue.toLongOrNull() in range.first..range.second) {
                    onValueChange(newValue.toLongOrNull() ?: value)
                }
            },
            textStyle = TextStyle(
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.weight(1f)
                .padding(horizontal = 8.dp)
        )

        IconButton(
            onClick = {
                val newValue = (textValue.toLongOrNull() ?: range.first)
                    .plus(1)
                    .coerceAtMost(range.second)
                textValue = newValue.toString()
                onValueChange(newValue)
            },
            enabled = (textValue.toLongOrNull() ?: range.first) < range.second
        ){
            Icon(
                imageVector = DataliftIcons.Increase,
                contentDescription = "Increase"
            )
        }
    }
}

@Preview
@Composable
private fun DataliftNumberPickerPreview() {
    MaterialTheme {
        DataliftNumberPicker(
            value = 1,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}