package com.datalift.model.data

import kotlinx.datetime.Instant

data class Post internal constructor(
    val postId: String,
    val posterId: String,
    val posterName: String,
    val posterProfilePicture: String?,
    val title: String,
    val content: String,
    val time: Instant,
    val isLiked: Boolean
) {
    constructor(postResource: PostResource, userData: UserData) : this(
        postId = postResource.postId,
        posterId = postResource.posterId,
        posterName = userData.userName,
        posterProfilePicture = userData.userProfileUrl,
        title = postResource.title,
        content = postResource.content,
        time = postResource.time,
        isLiked = userData.userID in postResource.usersLiked
    )
}

fun List<PostResource>.mapToPost(userData: UserData): List<Post> =
    map { Post(it, userData) }