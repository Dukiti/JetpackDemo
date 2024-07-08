package com.dungnm.example.compose.ui.activity.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dungnm.example.compose.ui.base.BaseScreen
import com.dungnm.example.compose.ui.theme.MainAppTheme

class RepoDetailScreen : BaseScreen<RepoDetailViewModel>() {

    @Composable
    override fun ContentView(viewModel: RepoDetailViewModel, innerPadding: PaddingValues) {
        val item by viewModel.itemDetail.collectAsState()
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = item?.owner?.avatarUrl ?: "",
                    contentDescription = "avatar",
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 32.dp)
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                )
                val textModifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .align(Alignment.Start)
                Text(
                    text = "Chủ sở hữu: ${item?.owner?.login}",
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = textModifier
                )
                Text(
                    text = "Tên: ${item?.fullName}",
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = textModifier
                )

                if (item?.description.isNullOrBlank()) {
                    Text(
                        text = "Mô tả: ${item?.description}",
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = textModifier
                    )
                }
                Text(
                    text = "Số sao: ${item?.stargazersCount}",
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = textModifier
                )
                Text(
                    text = "Ngôn ngữ: ${item?.language ?: "N/A"}",
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = textModifier
                )
                Text(
                    text = "Ngày tạo: ${item?.createdAt}",
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = textModifier
                )
                Text(
                    text = "Ngày cập nhật cuối: ${item?.updatedAt}",
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = textModifier
                )
            }
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