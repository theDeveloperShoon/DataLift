package com.datalift.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datalift.data.repository.OfflineFirstPostRepository
import com.datalift.ui.FeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val postRepository: OfflineFirstPostRepository
) : ViewModel() {

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 0)

    var isRefreshing by mutableStateOf(false)
        private set

    fun onRefresh() {
        // Adds this object to the flow
        // This will then trigger the refresh
        // caused by flatMapLatest
        refreshTrigger.tryEmit(Unit)
    }

    val feedState: StateFlow<FeedUiState> =
        refreshTrigger.onStart { emit(Unit) }
            .flatMapLatest {
                isRefreshing = true

                postRepository.getPostsResources()
                    .map(FeedUiState::Success)
                    .onCompletion { _ ->
//                        if (cause == null) {
//                            // Runs when there was no error
//                        }
                        isRefreshing = false
                    }
                    .catch { FeedUiState.Error }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = FeedUiState.Loading
            )

//    val feedState: StateFlow<FeedUiState> =
//        postRepository.getPostsResources()
//            .map(FeedUiState::Success)
//            .catch { FeedUiState.Error }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000),
//                initialValue = FeedUiState.Loading
//            )



    fun updateLikedStatus(postId: String, isLiked: Boolean) {
        viewModelScope.launch {
            postRepository.updatePostLikedStatus(postId, isLiked)
            refreshTrigger.emit(Unit)
        }
    }
}