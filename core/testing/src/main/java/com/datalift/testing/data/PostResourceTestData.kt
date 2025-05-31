package com.datalift.testing.data

import com.datalift.model.data.PostResource
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

val postResourceTestData: List<PostResource> = listOf(
    PostResource(
        postId = "1",
        posterId = "1",
        title = "Benching with the boys",
        content = "I benched a few with the boys",
        time = LocalDateTime(
            year = 2025,
            monthNumber = 5,
            dayOfMonth = 4,
            hour = 12,
            minute = 0,
            second = 0,
        ).toInstant(TimeZone.UTC),
        usersLiked = listOf("2","3")
    ),
    PostResource(
        postId = "2",
        posterId = "1",
        title = "Squatting Solo Dolo",
        content = "The boys were busy so I did squats by myself",
        time = Instant.parse("2025-05-14T00:00:00.000Z"),
        usersLiked = listOf("2")
    ),
    PostResource(
        postId = "3",
        posterId = "2",
        title = "Cardio with me, myself and I",
        content = "I love cardio, if you didn't know",
        time = Instant.parse("2025-05-13T00:00:00.000Z"),
        usersLiked = listOf("1")
    ),
)