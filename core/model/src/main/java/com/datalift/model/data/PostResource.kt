package com.datalift.model.data

import kotlinx.datetime.Instant

data class PostResource(
    val postId: String,
    val posterId: String,
    val title: String,
    val content: String,
    val time: Instant,
    val usersLiked: List<String>
)
