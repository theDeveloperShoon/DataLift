package com.datalift.data.repository

import com.datalift.database.service.PostService
import com.datalift.model.data.Post
import com.datalift.model.data.mapToPost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class OfflineFirstPostRepository @Inject constructor(
    private val userRepository: CompositeUserRepository,
    private val postServiceImpl: PostService
) : PostRepository {
    override fun getPostsResources(): Flow<List<Post>> =
        postServiceImpl.getFeedForUser(
            userId = userRepository.getCurrentUserId()
        ).combine(userRepository.userData) { posts, userData ->
            posts.mapToPost(userData)
        }

    override fun getPost(postId: String): Flow<Post> =
        postServiceImpl.getPost(
            postId = postId
        )

    override suspend fun updatePostLikedStatus(postId: String, isLiked: Boolean) {
        postServiceImpl.updatePostLikedStatus(
            postId = postId,
            isLiked = isLiked
        )
    }
}