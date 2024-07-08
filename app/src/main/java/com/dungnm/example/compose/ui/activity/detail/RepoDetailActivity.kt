package com.dungnm.example.compose.ui.activity.detail

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
class RepoDetailActivity : BaseActivity() {

    override var titleRes: Int = R.string.label_detail

    override val viewModel by viewModels<RepoDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainAppTheme {
                RepoDetailScreen().Screen(viewModel)
            }
        }
        initData()
    }


    private fun initData() {
        val jsonData = intent.getStringExtra(Tags.DATA)
        if (jsonData.isNullOrBlank()) {
            Log.e("3123213", "initData: data is not valid")
            return
        }
        val repoDetail: RepoEntity? = Gson().fromJson(jsonData, RepoEntity::class.java)
        if (repoDetail == null) {
            Log.e("3123213", "initData: item is null")
            return
        }
        viewModel.setItemDetail(item = repoDetail)
    }


}

