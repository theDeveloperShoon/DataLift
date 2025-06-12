package com.datalift.designsystem.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun DataliftVerticalScrollPicker(
    modifier: Modifier = Modifier,
    state: AmbiguousScrollState,
    inactiveItem: @Composable (Int) -> Unit,
    activeItem: @Composable (Int) -> Unit,
){
    val itemHeightPixels = remember{ mutableIntStateOf(28) }
    val itemHeightDp = with(LocalDensity.current) { itemHeightPixels.intValue.toDp() }

    val flingBehavior = rememberSnapFlingBehavior(lazyListState = state.listState)

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    LaunchedEffect(state.listState){
        snapshotFlow { state.listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect {
                state.currentIndex = it + state.visibleItemCount / 2
            }
    }

    Box(modifier = modifier){
        LazyColumn(
            state = state.listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .height(itemHeightDp * state.visibleItemCount)
                .fadingEdge(fadingEdgeGradient)
        ){
            items(state.itemCount + state.visibleItemCount - 1){ index ->
                if(index < state.visibleItemCount / 2
                    || index >= state.itemCount + state.visibleItemCount / 2)
                {
                    Spacer(
                        modifier = Modifier.height(itemHeightDp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .onSizeChanged { size ->
                                itemHeightPixels.intValue = size.height
                            }
                            .padding(
                                vertical = 2.dp
                            )
                    ) {
                        if(index == state.currentIndex) {
                            activeItem(index - state.visibleItemCount / 2)
                        } else {
                            inactiveItem(index - state.visibleItemCount / 2)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T> DataliftVerticalScrollPicker(
    modifier: Modifier = Modifier,
    state: VerticalScrollState<T>,
    item: @Composable (T) -> Unit,
    activeItem: @Composable (T) -> Unit,
){
    val itemHeightPixels = remember{ mutableIntStateOf(0) }
    val itemHeightDp = with(LocalDensity.current) { itemHeightPixels.intValue.toDp() }

    val flingBehavior = rememberSnapFlingBehavior(lazyListState = state.listState)

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    LaunchedEffect(state.listState){
        snapshotFlow { state.listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect {
                state.currentIndex = it + state.visibleItemCount / 2
            }
    }

    Box(modifier = modifier){
        LazyColumn(
            state = state.listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .height(itemHeightDp * state.visibleItemCount)
                .fadingEdge(fadingEdgeGradient)
        ){
            items(state.items.size + state.visibleItemCount - 1){ index ->
                if(index < state.visibleItemCount / 2
                    || index >= state.items.size + state.visibleItemCount / 2)
                {
                    Spacer(
                        modifier = Modifier.height(itemHeightDp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .onSizeChanged { size ->
                                itemHeightPixels.intValue = size.height
                            }
                            .padding(vertical = 8.dp)
                    ) {
                        if(index == state.currentIndex) {
                            activeItem(state.items[index - state.visibleItemCount])
                        } else {
                            item(state.items[index - state.visibleItemCount])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DataliftVerticalStringPicker(
    state: VerticalScrollState<String>,
    modifier: Modifier = Modifier,
){
    val itemHeightPixels = remember{ mutableIntStateOf(0) }
    val itemHeightDp = with(LocalDensity.current) { itemHeightPixels.intValue.toDp() }

    val flingBehavior = rememberSnapFlingBehavior(lazyListState = state.listState)

    LaunchedEffect(state.listState){
        snapshotFlow { state.listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect {
                state.currentIndex = it + state.visibleItemCount / 2
            }
    }

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    Box(modifier = modifier){
        LazyColumn(
            state = state.listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .height(itemHeightDp * state.visibleItemCount)
                .fadingEdge(fadingEdgeGradient)
        ){
            items(state.items.size + state.visibleItemCount - 1){ index ->
                if(index < state.visibleItemCount / 2
                    || index >= state.items.size + state.visibleItemCount / 2)
                {
                    Spacer(
                        modifier = Modifier.height(itemHeightDp)
                    )
                } else {
                    if(index == state.currentIndex) {
                        Text(
                            text = state.items[index - state.visibleItemCount / 2],
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .onSizeChanged { size ->
                                    itemHeightPixels.intValue = size.height
                                }
                        )
                    } else {
                        Text(
                            text = state.items[index - state.visibleItemCount / 2],
                            modifier = Modifier
                                .onSizeChanged { size ->
                                    itemHeightPixels.intValue = size.height
                                }
                        )
                    }
                }
            }
        }
    }

}

private val Int.isEven
    get() = this % 2 == 0

private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

data class VerticalScrollState<T>(
    val items: List<T>,
    val initialIndex: Int = 0,
    val visibleItemCount: Int
) {
    init {
        assert(!visibleItemCount.isEven) { "visibleItemsCount must be an odd number" }
    }

    val listState: LazyListState = LazyListState(
        firstVisibleItemIndex = initialIndex,
    )

    var currentIndex by mutableIntStateOf(initialIndex)

//    suspend fun scrollToItem(index: Int) = listState.scrollToItem(index)
}

data class AmbiguousScrollState(
    val itemCount: Int,
    val initialIndex: Int = 0,
    val visibleItemCount: Int
){
    init {
        assert(!visibleItemCount.isEven) { "visibleItemsCount must be an odd number" }
    }

    val listState: LazyListState = LazyListState(
        firstVisibleItemIndex = initialIndex,
    )

    var currentIndex by mutableIntStateOf(initialIndex)

    suspend fun scrollToItem(index: Int) = listState.scrollToItem(index)
}

fun <T> verticalScrollState(
    items: List<T>,
    visibleItemCount: Int,
    initialIndex: Int = 0
): VerticalScrollState<T> = VerticalScrollState(
    items = items,
    initialIndex = initialIndex,
    visibleItemCount = visibleItemCount
)

@Preview(showBackground = true)
@Composable
fun DataliftVerticalPickerPreview(){
    MaterialTheme {
        DataliftVerticalStringPicker(
            state = verticalScrollState(
                items = List(10) { "Item ${it + 1}" },
                initialIndex = 0,
                visibleItemCount = 5
            )
        )
    }
}