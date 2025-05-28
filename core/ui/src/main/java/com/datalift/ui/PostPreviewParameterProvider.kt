package com.datalift.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.datalift.model.data.Post
import com.datalift.model.data.PostResource
import com.datalift.model.data.UserData
import com.datalift.ui.PreviewParameterData.posts
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class PostPreviewParameterProvider : PreviewParameterProvider<List<Post>> {

    override val values: Sequence<List<Post>> = sequenceOf(posts)
}

object PreviewParameterData {
    private val userData: UserData = UserData(
        userID = "3",
        userName = "Sean Cotter",
        userProfileUrl = null
    )

    val posts = listOf(
        Post(
           postResource =  PostResource(
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
            userData = userData
        ),
        Post(
            postResource = PostResource(
                postId = "2",
                posterId = "1",
                title = "Squatting Solo Dolo",
                content = "The boys were busy so I did squats by myself",
                time = Instant.parse("2025-05-14T00:00:00.000Z"),
                usersLiked = listOf("2")
            ),
            userData = userData
        ),
        Post(
            postResource = PostResource(
                postId = "3",
                posterId = "2",
                title = "Cardio with me, myself and I",
                content = "I love cardio, if you didn't know",
                time = Instant.parse("2025-05-13T00:00:00.000Z"),
                usersLiked = listOf("1")
            ),
            userData = userData
        ),
    )
}