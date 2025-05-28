package com.datalift.designsystem.components

import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.datalift.designsystem.icon.DataliftIcons
import com.datalift.designsystem.theme.DataliftTheme

@Composable
fun DataliftLikeToggleButton(
    isLiked: Boolean,
    onToggleLike: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
    likedIcon: @Composable () -> Unit,
) {
    FilledIconToggleButton(
        checked = isLiked,
        onCheckedChange = onToggleLike,
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.iconToggleButtonColors(
            checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = if (isLiked) {
                MaterialTheme.colorScheme.onBackground.copy(
                    alpha = DataliftIconButtonDefaults.DISABLED_ICON_BUTTON_CONTAINER_ALPHA
                )
            } else {
                Color.Transparent
            }
        )
    ) {
        if(isLiked) likedIcon() else icon()
    }
}

@ThemePreviews
@Composable
private fun DataliftLikeButtonPreview(){
    DataliftTheme {
        DataliftLikeToggleButton(
            isLiked = true,
            onToggleLike = {},
            icon = {
                Icon(
                    imageVector = DataliftIcons.HeartBorder,
                    contentDescription = null,
                )
            },
            likedIcon = {
                Icon(
                    imageVector = DataliftIcons.Heart,
                    contentDescription = null
                )
            }
        )
    }
}

@ThemePreviews
@Composable
private fun DataliftLikeButtonPreviewUnliked(){
    DataliftTheme(
        disableDynamicThemeing = true
    ) {
        DataliftLikeToggleButton(
            isLiked = false,
            onToggleLike = {},
            icon = {
                Icon(
                    imageVector = DataliftIcons.HeartBorder,
                    contentDescription = null,
                )
            },
            likedIcon = {
                Icon(
                    imageVector = DataliftIcons.Heart,
                    contentDescription = null
                )
            }
        )
    }
}

object DataliftIconButtonDefaults {
    const val DISABLED_ICON_BUTTON_CONTAINER_ALPHA = 0.5f
}