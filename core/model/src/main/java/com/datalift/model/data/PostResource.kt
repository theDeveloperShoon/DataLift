package com.datalift.model.data

import kotlinx.datetime.Instant

/**
 * Data class representing a post resource.
 *
 * This is a post that is available from the PostList collection in the database
 *
 * @property postId The ID of the post.
 * @property posterId The ID of the poster.
 * @property title The title of the post.
 * @property content The content of the post.
 * @property time The time when the post was created.
 * @property usersLiked A list of user IDs who have liked the post.
 */
data class PostResource(
    val postId: String,
    val posterId: String,
    val title: String,
    val content: String,
    val time: Instant,
    val usersLiked: List<String>
)
