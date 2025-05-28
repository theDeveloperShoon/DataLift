package com.datalift.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.datalift.designsystem.theme.DataliftTheme
import com.datalift.model.data.Post
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun PostCard(
    post: Post,
    isLiked: Boolean,
    onToggleLike: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card (
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(16.dp)){
            Row {
                PostCardProfileImage(post.posterProfilePicture)
                PostCardAuthorAndTime(
                    name = post.posterName,
                    time = post.time,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            PostCardTitle(title = post.title)
            PostCardDescription(postDescription = post.content)
            Row(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                LikedButton(
                    isLiked = isLiked,
                    onClick = onToggleLike
                )
            }
        }
    }
}

@Composable
fun dateFormatted(publishTime: Instant): String = DateTimeFormatter
    .ofPattern("MMMM dd, yyyy 'at' HH:mm a")
    .withLocale(Locale.getDefault())
    .withZone(LocalTimeZone.current.toJavaZoneId())
    .format(publishTime.toJavaInstant())

@Composable
fun PostCardTitle(
    title: String
){
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PostCardAuthorAndTime(
    name: String,
    time: Instant,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        PostCardAuthor(name)
        PostCardTimestamp(time)
    }
}

@Composable
fun PostCardAuthor(
    author: String
){
    Text(
        text = author,
        style = MaterialTheme.typography.labelLarge,
    )
}

@Composable
fun PostCardDescription(
    postDescription: String,
){
    Text(
        text = postDescription,
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
fun PostCardTimestamp(
    publishTime: Instant,
){
    val formattedDate = dateFormatted(publishTime)
    Text(
        text = formattedDate,
        style = MaterialTheme.typography.labelSmall,
    )
}

@Composable
fun PostCardProfileImage(
    profileImageUrl: String?
) {
    ProfileImage(
        profileImageUrl = profileImageUrl,
    )
}

@Preview("PostCard")
@Composable
private fun PostCardPreview(
    @PreviewParameter(PostPreviewParameterProvider::class)
    posts: List<Post>,
) {
    CompositionLocalProvider(
        LocalInspectionMode provides true
    ) {
        DataliftTheme {
            Surface {
                PostCard(
                    post = posts[0],
                    isLiked = true,
                    onToggleLike = {},
                    onClick = {},
                )
            }
        }
    }
}