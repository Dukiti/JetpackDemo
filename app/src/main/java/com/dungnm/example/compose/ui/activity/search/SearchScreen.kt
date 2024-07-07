package com.dungnm.example.compose.ui.activity.search

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dungnm.example.compose.model.response.RepoEntity
import com.dungnm.example.compose.ui.activity.home.HomeActivity
import com.dungnm.example.compose.ui.base.BaseScreen
import com.dungnm.example.compose.ui.theme.MainAppTheme

class SearchScreen : BaseScreen<SearchViewModel>() {

    @Composable
    override fun Screen(viewModel: SearchViewModel) {
        super.Screen(viewModel)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ContentView(viewModel: SearchViewModel, innerPadding: PaddingValues) {
        val context = LocalContext.current
        val activity = LocalContext.current as? Activity
        val focusManager = LocalFocusManager.current
        val searchText by viewModel.searchText.collectAsState()
        val listItem by viewModel.listRepo.collectAsState()
        val pageIndex by viewModel.pageIndex.collectAsState()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(query = searchText,
                onQueryChange = viewModel::onSearch,
                onSearch = { text ->
                    viewModel.onSearch(text)
                    focusManager.clearFocus()
                },
                active = false,
                onActiveChange = {},
                placeholder = {
                    Text(text = "Search repo")
                },
                enabled = true,
                modifier = Modifier
                    .padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                content = {})

            if (listItem.isNotEmpty()) {
                LazyColumn(Modifier.weight(1f)) {
                    Log.e("213981273", "ContentView: ${listItem.size}")
                    items(count = listItem.size + 1,
                        key = { listItem.getOrNull(it)?.id ?: "PLACEHOLDER" }) { index ->
                        val repoEntity = listItem.getOrNull(index)
                        if (repoEntity != null) {
                            RepoItem(repoEntity) {
                                gotoDetail(activity, repoEntity)
                            }
                        } else {
                            PageView(
                                pageIndex,
                                onPre = viewModel::onPrePage,
                                onNext = viewModel::onNextPage
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun RepoItem(item: RepoEntity? = null, onclick: (() -> Unit)? = null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .wrapContentHeight()
                .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                .padding(8.dp)
                .clickable {
                    onclick?.invoke()
                },
        ) {
            Row {
                AsyncImage(
                    model = item?.owner?.avatarUrl,
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterVertically)
                        .border(1.dp, Color.Gray, CircleShape)
                )
                Text(
                    text = item?.fullName ?: item?.name ?: "N/A",
                    color = Color.Blue,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth()
                )
            }
            if (!item?.description.isNullOrEmpty()) {
                Text(
                    item?.description ?: "N/A",
                    color = Color.Black,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                "• Updated on ${item?.updatedAt}",
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis,
                fontSize = 10.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    private fun PageView(page: Int, onNext: (() -> Unit)? = null, onPre: (() -> Unit)? = null) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val iconModifier = Modifier
                .size(48.dp)
                .padding(8.dp)
            Image(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                colorFilter = ColorFilter.tint(Color.Gray),
                contentDescription = "back",
                modifier = iconModifier
                    .clickable(enabled = page > 1) {
                        onPre?.invoke()
                    }
                    .alpha(if (page == 1) 0.2f else 1f),
            )
            Text(text = "$page", fontSize = 32.sp, color = Color.Blue)
            Image(imageVector = Icons.Rounded.ArrowForwardIos,
                contentDescription = "next",
                colorFilter = ColorFilter.tint(Color.Gray),
                modifier = iconModifier.clickable {
                    onNext?.invoke()
                })
        }
    }

    private fun gotoDetail(activity: Activity?, repoEntity: RepoEntity) {
        val intent = Intent(activity, HomeActivity::class.java)
        activity?.startActivity(intent)
    }

    @Preview(showBackground = true, heightDp = 100)
    @Composable
    fun PreviewItem() {
        MainAppTheme {
            RepoItem()
        }
    }

    @Preview(showBackground = true, heightDp = 100)
    @Composable
    fun PreviewPageView() {
        MainAppTheme {
            PageView(1)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Preview() {
        MainAppTheme {
            Screen(hiltViewModel())
        }
    }
}