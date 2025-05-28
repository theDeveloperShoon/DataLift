package com.datalift.data.repository

import com.datalift.model.data.Post
import kotlinx.coroutines.flow.Flow

interface UserResourceRepository {
    fun observeAllPosts() : Flow<List<Post>>
}