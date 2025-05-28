package com.datalift.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.datalift.designsystem.components.DevicePreviews
import com.datalift.designsystem.theme.DataliftTheme
import com.datalift.model.data.Post
import com.datalift.ui.FeedUiState
import com.datalift.ui.PostPreviewParameterProvider
import com.datalift.ui.postFeed

@Composable
internal fun FeedScreen(
    onPostClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel(),
) {
    val feedState by viewModel.feedState.collectAsStateWithLifecycle()
    val isRefreshing = viewModel.isRefreshing

    FeedScreen(
        uiState = feedState,
        isRefreshing = isRefreshing,
        onRefresh = viewModel::onRefresh,
        onPostClick = onPostClick,
        onPostCheckedChange = viewModel::updateLikedStatus,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedScreen(
    uiState: FeedUiState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onPostClick: (String) -> Unit,
    onPostCheckedChange: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        val state = rememberLazyListState()

        LazyColumn(
            state = state,
            modifier = Modifier.fillMaxSize()
        ) {
            postFeed(
                feedState = uiState,
                onPostClick = onPostClick,
                onPostCheckedChange = onPostCheckedChange,
            )
        }
    }
}

@DevicePreviews
@Composable
private fun FeedScreenLoading(){
    DataliftTheme {
        FeedScreen(
            uiState = FeedUiState.Loading,
            isRefreshing = false,
            onRefresh = {},
            onPostClick = {},
            onPostCheckedChange = { _, _ -> },
        )
    }
}

@DevicePreviews
@Composable
private fun FeedScreenPopulatedRefreshing(
    @PreviewParameter(PostPreviewParameterProvider::class)
    postList: List<Post>
){
    DataliftTheme {
        FeedScreen(
            uiState = FeedUiState.Success(postList),
            isRefreshing = true,
            onRefresh = {},
            onPostClick = {},
            onPostCheckedChange = { _, _ -> },
        )
    }
}

@DevicePreviews
@Composable
private fun FeedScreenPopulated(
    @PreviewParameter(PostPreviewParameterProvider::class)
    postList: List<Post>
){
    DataliftTheme {
        FeedScreen(
            uiState = FeedUiState.Success(postList),
            isRefreshing = false,
            onRefresh = {},
            onPostClick = {},
            onPostCheckedChange = { _, _ -> },
        )
    }
}