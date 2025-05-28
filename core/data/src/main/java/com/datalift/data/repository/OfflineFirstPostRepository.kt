package com.datalift.data.repository

import com.datalift.database.service.PostService
import com.datalift.model.data.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstPostRepository @Inject constructor(
    private val userRepository: CompositeUserRepository,
    private val postServiceImpl: PostService
) : PostRepository {
    override fun getPostsResources(): Flow<List<Post>> =
        postServiceImpl.getFeedForUser(
            userId = userRepository.getCurrentUserId()
        )

    override fun getPost(postId: String): Flow<Post> =
        postServiceImpl.getPost(
            postId = postId
        )

    override suspend fun updatePostLikedStatus(postId: String, isLiked: Boolean) {

        TODO("Not yet implemented")
    }
}