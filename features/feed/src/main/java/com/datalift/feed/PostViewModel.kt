package com.datalift.feed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.datalift.data.repository.PostRepository
import com.datalift.feed.navigation.PostRoute
import com.datalift.model.data.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PostRepository
) : ViewModel(){
    private val postRouteId = savedStateHandle.toRoute<PostRoute>()

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 0)

    val uiState: StateFlow<PostUiState> =
        refreshTrigger.onStart { emit(Unit) }
            .flatMapLatest {
                repository.getPost(postRouteId.id)
                    .map(PostUiState::Success)
                    .catch { PostUiState.Error }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PostUiState.Loading
            )

    fun updateLikedStatus(postId: String, isLiked: Boolean) {
        viewModelScope.launch {
            repository.updatePostLikedStatus(postId, isLiked)
            refreshTrigger.emit(Unit)
        }
    }
}

sealed interface PostUiState {
    data object Loading : PostUiState
    data class Success(val post: Post) : PostUiState
    data object Error : PostUiState

}