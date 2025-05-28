package com.datalift.data.repository

import com.datalift.model.data.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPostsResources() : Flow<List<Post>>
    fun getPost(postId: String) : Flow<Post>
    suspend fun updatePostLikedStatus(postId: String, isLiked: Boolean)
}