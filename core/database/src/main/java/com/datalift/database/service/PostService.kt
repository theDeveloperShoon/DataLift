package com.datalift.database.service

import com.datalift.model.data.Post
import kotlinx.coroutines.flow.Flow

interface PostService {
    fun getFeedForUser(userId: String) : Flow<List<Post>>
    fun getPost(postId: String) : Flow<Post>
    suspend fun updatePostLikedStatus(postId: String, isLiked: Boolean)
}