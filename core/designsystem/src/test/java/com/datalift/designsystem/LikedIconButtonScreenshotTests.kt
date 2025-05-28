package com.datalift.designsystem

import androidx.activity.ComponentActivity
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.datalift.designsystem.components.DataliftLikeToggleButton
import com.datalift.designsystem.icon.DataliftIcons
import com.datalift.screenshot_testing.captureMultiTheme
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class, qualifiers = "480dpi")
@LooperMode(LooperMode.Mode.PAUSED)
class LikedIconButtonScreenshotTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun likedIconButton_multipleThemes(){
        composeTestRule.captureMultiTheme("LikedIconButton") {
            DataliftLikedButtonExample(true)
        }
    }

    @Test
    fun likedIconButton_unliked_multipleThemes() {
        composeTestRule.captureMultiTheme("LikedIconButtonUnliked") {
            Surface {
                DataliftLikedButtonExample(false)
            }
        }
    }

    @Composable
    private fun DataliftLikedButtonExample(checked: Boolean){
        DataliftLikeToggleButton(
            isLiked = checked,
            onToggleLike = {_ ->},
            icon = {
                Icon(
                    imageVector = DataliftIcons.HeartBorder,
                    contentDescription = null,
                )
            },
            likedIcon = {
                Icon(
                    imageVector = DataliftIcons.Heart,
                    contentDescription = null
                )
            }
        )
    }
}