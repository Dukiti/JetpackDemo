package com.dungnm.example.compose.ui.activity.search

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.dungnm.example.compose.R
import com.dungnm.example.compose.core.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    override var titleRes: Int = R.string.label_search

    override val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView {
            SearchScreen().Screen(viewModel)
        }
//        viewModel.searchRepoGithub("github search")
    }

}

