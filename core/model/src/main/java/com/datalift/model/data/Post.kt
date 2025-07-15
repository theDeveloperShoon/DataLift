package com.datalift.model.data

import kotlinx.datetime.Instant

/**
 * Data class representing a post.
 *
 * It combines [PostResource] and [UserData] to provide a complete representation of a post.
 *
 * @property postId The ID of the post.
 * @property posterId The ID of the poster.
 * @property posterName The name of the poster.
 * @property posterProfilePicture The URL of the poster's profile picture.
 * @property title The title of the post.
 * @property content The content of the post.
 * @property time The time when the post was created.
 * @property isLiked Whether the current user has liked the post.
 *
 * @see PostResource
 * @see UserData
 */
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