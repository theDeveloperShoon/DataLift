package com.datalift.designsystem.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.datalift.designsystem.icon.DataliftIcons
import com.datalift.designsystem.theme.DataliftTheme

@Composable
fun IconButtonDisplayToggle(
    icon: ImageVector,
    contentDescription: String? = null,
    displayVisible: Boolean,
    toggleDisplay: () -> Unit,
    display: @Composable () -> Unit,
){
    IconButton(
        onClick = toggleDisplay
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }

    if(displayVisible){
        display()
    }
}

@Preview
@Composable
private fun IconButtonDisplayTogglePreview(){
    DataliftTheme {
        IconButtonDisplayToggle(
            icon = DataliftIcons.More,
            displayVisible = false,
            toggleDisplay = {},
            display = {

            },
        )
    }
}