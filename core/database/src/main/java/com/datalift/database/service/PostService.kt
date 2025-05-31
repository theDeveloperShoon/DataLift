package com.datalift.database.service

import com.datalift.model.data.Post
import com.datalift.model.data.PostResource
import kotlinx.coroutines.flow.Flow

interface PostService {
    fun getFeedForUser(userId: String) : Flow<List<PostResource>>
    fun getPost(postId: String) : Flow<Post>
    suspend fun updatePostLikedStatus(postId: String, isLiked: Boolean)
}