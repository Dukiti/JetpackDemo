package com.dungnm.example.compose.home.ui.detail

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dungnm.example.compose.core.base.BaseScreen
import com.dungnm.example.compose.core.constants.Tags
import com.dungnm.example.compose.core.navigation.rememberMainNav
import com.dungnm.example.compose.core.ui.theme.ExtendTheme
import com.dungnm.example.compose.home.R
import com.dungnm.example.compose.home.model.res.RepoEntity
import com.google.gson.Gson

class RepoDetailScreen : BaseScreen<RepoDetailViewModel>() {

    @Composable
    override fun ContentView(viewModel: RepoDetailViewModel, innerPadding: PaddingValues) {
        val mainNav = rememberMainNav()
        val dataJson = mainNav.currentBackStackEntry?.arguments?.getString(Tags.DATA)
        viewModel.setItemDetail(Gson().fromJson(dataJson, RepoEntity::class.java))
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
                    text = stringResource(id = R.string.label_owner_s, item?.owner?.login ?: "N/A"),
                    color = ExtendTheme.current.colorDescription,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = textModifier
                )
                Text(
                    text = stringResource(id = R.string.label_name_s, item?.fullName ?: "N/A"),
                    color = ExtendTheme.current.colorDescription,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = textModifier
                )

                if (item?.description.isNullOrBlank()) {
                    Text(
                        text = stringResource(
                            id = R.string.label_description_s, item?.description ?: "N/A"
                        ),
                        color = ExtendTheme.current.colorDescription,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = textModifier
                    )
                }
                Text(
                    text = stringResource(
                        id = R.string.label_star_s, item?.stargazersCount ?: "N/A"
                    ),
                    color = ExtendTheme.current.colorDescription,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = textModifier
                )
                Text(
                    text = stringResource(
                        id = R.string.label_language_s, item?.language ?: "N/A"
                    ),
                    color = ExtendTheme.current.colorDescription,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = textModifier
                )
                Text(
                    text = stringResource(
                        id = R.string.label_created_at_s, item?.createdAt ?: "N/A"
                    ),
                    color = ExtendTheme.current.colorDescription,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = textModifier
                )
                Text(
                    text = stringResource(
                        id = R.string.label_updated_at_s, item?.updatedAt ?: "N/A"
                    ),
                    color = ExtendTheme.current.colorDescription,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = textModifier
                )
            }
        }
    }


//    @Preview(showBackground = true)
//    @Composable
//    fun Preview() {
//        MainAppTheme {
//            Screen(hiltViewModel())
//        }
//    }


}