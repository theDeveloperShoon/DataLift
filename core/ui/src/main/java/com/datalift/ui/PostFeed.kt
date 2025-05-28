package com.datalift.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.datalift.designsystem.components.DataliftLoadingIcon
import com.datalift.designsystem.theme.DataliftTheme
import com.datalift.model.data.Post

fun LazyListScope.postFeed(
    feedState: FeedUiState,
    onPostClick: (String) -> Unit,
    onPostCheckedChange: (String, Boolean) -> Unit,
) {
    when (feedState) {
        FeedUiState.Error -> Unit // TODO: Change to provide an error
        FeedUiState.Loading -> {
            item {
                Box(
                    modifier = Modifier.fillParentMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    DataliftLoadingIcon(
                        contentDesc = "Loading Posts",
                    )
                }
            }
        }
        is FeedUiState.Success -> {
            items(feedState.posts){ post ->
                PostCard(
                    post = post,
                    isLiked = post.isLiked,
                    onToggleLike = {
                        onPostCheckedChange(
                            post.postId,
                            !post.isLiked
                        )
                    },
                    onClick = {
                        onPostClick(post.postId)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
}

sealed interface FeedUiState {
    data object Loading : FeedUiState
    data class Success(val posts: List<Post>) : FeedUiState
    data object Error : FeedUiState
}

@Preview
@Composable
private fun PostFeedLoadingPreview(){
    DataliftTheme {
        LazyColumn {
            postFeed(
                feedState = FeedUiState.Loading,
                onPostClick = {},
                onPostCheckedChange = { _, _ -> }
            )
        }
    }
}

@Preview
@Composable
private fun PostFeedContentPreview(
    @PreviewParameter(PostPreviewParameterProvider::class)
    postList: List<Post>
){
    CompositionLocalProvider(
        LocalInspectionMode provides true
    ) {
        DataliftTheme {
            LazyColumn {
                postFeed(
                    feedState = FeedUiState.Success(postList),
                    onPostClick = {},
                    onPostCheckedChange = { _, _ -> }
                )
            }
        }
    }
}