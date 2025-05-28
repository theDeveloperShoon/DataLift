package com.datalift.ui

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.datalift.designsystem.components.DataliftLikeToggleButton
import com.datalift.designsystem.icon.DataliftIcons

@Composable
fun LikedButton(
    isLiked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    DataliftLikeToggleButton(
        isLiked = isLiked,
        onToggleLike = { onClick() },
        modifier = modifier,
        icon = {
            Icon(
                imageVector = DataliftIcons.HeartBorder,
                contentDescription = stringResource(R.string.core_ui_like)
            )
        },
        likedIcon = {
            Icon(
                imageVector = DataliftIcons.Heart,
                contentDescription = stringResource(R.string.core_ui_unlike)
            )
        },
    )
}