package com.datalift.data.repository

import com.datalift.model.data.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompositeUserResourceRepository @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) : UserResourceRepository {
    override fun observeAllPosts(): Flow<List<Post>> =
        postRepository.getPostsResources(
//            currentUserId =  userRepository.getCurrentUserId()
        )

}