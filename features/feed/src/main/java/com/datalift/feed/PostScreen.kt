package com.datalift.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.datalift.designsystem.components.DataliftLoadingIcon
import com.datalift.designsystem.components.DevicePreviewWithBackground
import com.datalift.designsystem.icon.DataliftIcons
import com.datalift.designsystem.theme.DataliftTheme
import com.datalift.model.data.Post
import com.datalift.ui.LikedButton
import com.datalift.ui.LocalTimeZone
import com.datalift.ui.PostPreviewParameterProvider
import com.datalift.ui.ProfileImage
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun PostScreen(
    navUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PostScreen(
        postUiState = uiState,
        navUp = navUp,
        toggleLike = viewModel::updateLikedStatus,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PostScreen(
    postUiState: PostUiState,
    navUp: () -> Unit,
    toggleLike: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier) {
        TopAppBar(
            title = {
                Text(text = "Post")
            },
            navigationIcon = {
                IconButton(
                    onClick = navUp
                ) {
                    Icon(
                        imageVector = DataliftIcons.NavigateUp,
                        contentDescription = null,
                    )
                }
            }
        )
        when (postUiState) {
            PostUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ){
                    Text(
                        text = stringResource(R.string.post_ui_error_message),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            PostUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    DataliftLoadingIcon(
                        contentDesc = ""
                    )
                }
            }
            is PostUiState.Success -> {
                val post = postUiState.post
                PostScreenBody(
                    post = post,
                    isLiked = post.isLiked,
                    onToggleLike = {
                        toggleLike(post.postId,!post.isLiked)
                        // TODO: MIGHT NEED TO MODIFY isLiked to a var
                        // TODO: TO MODIFY THE Like Status
                    },
                )

            }
        }
    }
}

@Composable
fun PostScreenBody(
    post: Post,
    isLiked: Boolean,
    onToggleLike: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Row{
            PostScreenProfileImage(post.posterProfilePicture)
            PostScreenMetadata(
                authorName = post.posterName,
                time = post.time,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        PostScreenTitle(post.title)
        PostScreenDescription(post.content)
        // TODO: ADD WORKOUT CONTENT
        // TODO: ADD WORKOUT GRAPH
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            LikedButton(
                isLiked = isLiked,
                onClick = onToggleLike
            )
            // TODO: ADD SHARE BUTTON
        }
    }
}

@Composable
fun PostScreenProfileImage(
    profileImageUrl: String?
) {
    ProfileImage(
        profileImageUrl = profileImageUrl,
        size = Pair(64.dp, 64.dp)
    )
}

@Composable
fun PostScreenMetadata(
    authorName: String,
    time: Instant,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        PostScreenAuthor(authorName)
        PostScreenTimestamp(time)
    }
}

@Composable
fun PostScreenAuthor(
    author: String
) {
    Text(
        text = author,
        style = MaterialTheme.typography.labelLarge,
    )
}

@Composable
fun PostScreenTimestamp(
    publishTime: Instant,
) {
    val formattedDate = dateFormatted(publishTime)
    Text(
        text = formattedDate,
    )
}

@Composable
fun dateFormatted(publishTime: Instant): String = DateTimeFormatter
    .ofPattern("MMMM dd, yyyy 'at' HH:mm a")
    .withLocale(Locale.getDefault())
    .withZone(LocalTimeZone.current.toJavaZoneId())
    .format(publishTime.toJavaInstant())

@Composable
fun PostScreenTitle(
    title: String
){
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PostScreenDescription(
    description: String
) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
    )
}

@DevicePreviewWithBackground
@Composable
private fun PostScreenErrorPreview(){
    DataliftTheme {
        PostScreen(
            postUiState = PostUiState.Error,
            toggleLike = { _, _ -> },
            navUp = {},
        )
    }
}

@DevicePreviewWithBackground
@Composable
private fun PostScreenLoadingPreview(){
    DataliftTheme {
        PostScreen(
            postUiState = PostUiState.Loading,
            toggleLike = { _, _ -> },
            navUp = {},
        )
    }
}

@DevicePreviewWithBackground
@Composable
private fun PostScreenSuccessPreview(
    @PreviewParameter(PostPreviewParameterProvider::class)
    posts: List<Post>,
){
    CompositionLocalProvider(
        LocalInspectionMode provides true
    ) {
        DataliftTheme {
            Surface {
                PostScreen(
                    postUiState = PostUiState.Success(posts[0]),
                    toggleLike = { _, _ -> },
                    navUp = {},
                )
            }
        }
    }
}