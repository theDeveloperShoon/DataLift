package com.datalift.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.datalift.designsystem.components.AmbiguousScrollState
import com.datalift.designsystem.components.DataliftVerticalScrollPicker
import com.datalift.designsystem.theme.DataliftTheme
import kotlinx.coroutines.launch

@Composable
fun VerticalNumberPicker(
    modifier: Modifier = Modifier,
    values: List<Int>,
    onValueChange: (Int) -> Unit
){
    val scope = rememberCoroutineScope()

    val scrollState = remember {
        AmbiguousScrollState(
            itemCount = values.size,
            initialIndex = values.indexOf(values.first()),
            visibleItemCount = 5
        )
    }

    val textStyle = TextStyle(
        fontSize = 40.sp,
    )

    DataliftVerticalScrollPicker(
        modifier = modifier,
        state = scrollState,
        activeItem = { index ->
            VerticalScrollEditText(
                value = values[index].toString(),
                onValueChange = {
                    val num = it.toInt()
                    onValueChange(num)
                    val newIndex = values.indexOf(num)
                    scrollState.currentIndex = newIndex
                    scope.launch {
                        scrollState.scrollToItem(newIndex)
                    }
                },
                predicate = {
                    val num = it.toIntOrNull() ?: false
                    num in values
                },
                style = textStyle
            )
//            Text(
//                text = values[index].toString(),
//                fontWeight = FontWeight.Bold
//            )
        },
        onIndexChange = { index ->
            onValueChange(values[index])
            scrollState.currentIndex = index
        },
        inactiveItem = { index ->
            Text(
                text = values[index].toString(),
                style = textStyle,
            )
        }
    )
}

@Composable
private fun VerticalScrollEditText(
    value: String,
    onValueChange: (String) -> Unit,
    predicate: (String) -> Boolean,
    style: TextStyle,
    modifier: Modifier = Modifier
){
    val skc = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    var buffer by remember {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(
                    start = 0,
                    end = 0
                )
            )
        )
    }

    val action: KeyboardActionScope.() -> Unit = {
        if(predicate(value)){
            onValueChange(buffer.text)
        } else {
            focusRequester.freeFocus()
            onValueChange(value)
        }
        skc?.hide()
    }

    val containerModifier = modifier
        .onFocusChanged {
            if (it.isFocused || it.hasFocus) {
                buffer = buffer.copy(
                    selection = TextRange(
                        start = 0,
                        end = buffer.text.length
                    )
                )
            }
        }
        .focusRequester(focusRequester)
        .width(IntrinsicSize.Min)

    Box(
        modifier = containerModifier
    ) {
        BasicTextField(
            value = buffer,
            onValueChange = {
                buffer = it
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = action
            ),
            textStyle = style.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = containerModifier
        )
    }
}

@Preview
@Composable
private fun VerticalScrollEditTextPreview(){
    VerticalScrollEditText(
        value = "",
        onValueChange = {},
        predicate = { false },
        style = TextStyle(
            fontSize = 48.sp
        )
    )
}

@Preview
@Composable
private fun VerticalNumberPickerPreview(){
    VerticalNumberPicker(
        values = List(10) { it + 1 },
        onValueChange = {}
    )
}

@Preview
@Composable
private fun FullScreenVerticalNumberPickerPreview(){
    DataliftTheme(darkTheme = true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            VerticalNumberPicker(
                values = List(10) { it + 1 },
                onValueChange = {}
            )
        }
    }
}