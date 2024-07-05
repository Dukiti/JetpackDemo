package com.dungnm.example.compose.ui.activity.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dungnm.example.compose.model.ImageGallery
import com.dungnm.example.compose.model.PlaceholderState
import com.dungnm.example.compose.ui.common.ToolBar


@Composable
fun GalleryScreen(vm: GalleryViewModel) {
    val context = LocalContext.current
    Scaffold(topBar = {
        ToolBar()
    }) { innerPadding ->
        val firstPageState = vm.firstPageStateFlow.collectAsState().value
        when (firstPageState) {
            PlaceholderState.Loading -> ItemLoading(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            )

            is PlaceholderState.Failure -> FailureItem(
                throwable = firstPageState.throwable,
                onRetry = { vm.retry(context) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            )

            is PlaceholderState.Idle -> {
                if (firstPageState.isEmpty) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            "Empty list",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                } else {
                    val userList by vm.galleryStateFlow.collectAsState()
                    val loadingState by vm.loadingStateFlow.collectAsState()

                    PhotoList(
                        state = loadingState,
                        users = userList,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        onRetry = { vm.retry(context) },
                        loadNextPage = { vm.loadNextPage(context) },
                    )
                }
            }
        }
    }
}

@Composable
fun PhotoList(
    state: PlaceholderState,
    users: List<ImageGallery>,
    modifier: Modifier,
    onRetry: () -> Unit,
    loadNextPage: () -> Unit,
) {
    val threshold = 3
    val lastIndex = users.lastIndex

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 128.dp),
        verticalItemSpacing = 1.dp,
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        modifier = modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.spacedBy(12.dp),
//        contentPadding = PaddingValues(all = 8.dp),
    ) {
        items(
            count = users.size + 1,
            key = { users.getOrNull(it)?.id ?: "PLACEHOLDER" },
        ) { index ->
            val item = users.getOrNull(index)
            val parentMaxWidth = Modifier.fillMaxWidth()

            if (item != null) {
                if (index + threshold >= lastIndex) {
                    SideEffect {
                        loadNextPage()
                    }
                }

                AsyncImage(
                    model = item.path,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                )

//                Column(
//                    modifier = parentMaxWidth,
//                ) {
//                    Text(
//                        text = item.id ?: " ----",
//                        style = MaterialTheme.typography.titleSmall,
//                    )
//                    Spacer(modifier = Modifier.height(4.dp))
//                    Text(
//                        text = item.path ?: "----",
//                        style = MaterialTheme.typography.titleSmall,
//                    )
//                }

                return@items
            }

            when (state) {
                is PlaceholderState.Failure -> FailureItem(
                    throwable = state.throwable,
                    onRetry = onRetry,
                    modifier = parentMaxWidth,
                )

                is PlaceholderState.Idle -> if (!state.isEmpty) {
                    Spacer(modifier = parentMaxWidth.requiredHeight(48.dp))
                }

                PlaceholderState.Loading -> ItemLoading(modifier = parentMaxWidth)
            }
        }
    }
}

@Composable
fun ItemLoading(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun FailureItem(
    throwable: Throwable,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            throwable.message ?: "Unknown error",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text(text = "RETRY")
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        GalleryScreen(
            GalleryViewModel()
        )
    }
}