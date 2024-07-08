package com.dungnm.example.compose.ui.activity.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.dungnm.example.compose.R
import com.dungnm.example.compose.constants.Tags
import com.dungnm.example.compose.model.response.RepoEntity
import com.dungnm.example.compose.ui.base.BaseActivity
import com.dungnm.example.compose.ui.theme.MainAppTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    override var titleRes: Int = R.string.label_search

    override val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainAppTheme {
                SearchScreen().Screen(viewModel)
            }
        }
//        viewModel.searchRepoGithub("github search")
    }


}

