package example.mardsoul.draganddroplazycolumn.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import example.mardsoul.draganddroplazycolumn.R
import example.mardsoul.draganddroplazycolumn.ui.components.rememberDragAndDropListState
import example.mardsoul.draganddroplazycolumn.util.move
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun LazyColumnApp(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val users = uiState.list.toMutableStateList()
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var overscrollJob by remember { mutableStateOf<Job?>(null) }
    val dragAndDropListState =
        rememberDragAndDropListState(lazyListState) { from, to ->
            users.move(from, to)
        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.medium_padding))
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDrag = { change, offset ->
                            change.consume()
                            dragAndDropListState.onDrag(offset)

                            if (overscrollJob?.isActive == true) return@detectDragGesturesAfterLongPress

                            dragAndDropListState
                                .checkOverscroll()
                                .takeIf { it != 0f }
                                ?.let {
                                    overscrollJob = coroutineScope.launch {
                                        dragAndDropListState.lazyListState.scrollBy(it)
                                    }
                                } ?: run { overscrollJob?.cancel() }

                        },
                        onDragStart = { offset ->
                            dragAndDropListState.onDragStart(offset)
                        },
                        onDragEnd = { dragAndDropListState.onDragInterrupted() },
                        onDragCancel = { dragAndDropListState.onDragInterrupted() }
                    )
                },
            state = dragAndDropListState.lazyListState
        ) {
            itemsIndexed(users) { index, user ->
                ItemCard(
                    yaItem = user,
                    modifier = Modifier
                        .composed {
                            val offsetOrNull =
                                dragAndDropListState.elementDisplacement.takeIf {
                                    index == dragAndDropListState.currentIndexOfDraggedItem
                                }
                            Modifier.graphicsLayer {
                                translationY = offsetOrNull ?: 0f
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun ItemCard(
    yaItem: YandexMusicItem,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(6.dp))
            ) {
                Image(
                    painter = painterResource(id = yaItem.img),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null
                )
            }
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = yaItem.name,
                    modifier = Modifier,
                    color = Color.White
                )
                Text(
                    text = yaItem.author,
                    modifier = Modifier,
                    color = Color(0xFFb6b6b6)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.Menu,
                modifier = Modifier.size(30.dp),
                tint = Color(0xFFb6b6b6),
                contentDescription = null
            )
        }
    }
}