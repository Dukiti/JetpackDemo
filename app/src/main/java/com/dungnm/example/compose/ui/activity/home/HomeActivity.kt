package com.dungnm.example.compose.ui.activity.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.dungnm.example.compose.R
import com.dungnm.example.compose.core.base.BaseActivity
import com.dungnm.example.compose.ui.activity.CommonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    override val titleRes: Int = R.string.app_name

    override val viewModel: CommonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView {
            HomeScreen().Screen(viewModel)
        }
    }
}

