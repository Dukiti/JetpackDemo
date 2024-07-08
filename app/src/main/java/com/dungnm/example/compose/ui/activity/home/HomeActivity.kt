package com.dungnm.example.compose.ui.activity.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.dungnm.example.compose.ui.activity.CommonViewModel
import com.dungnm.example.compose.ui.base.BaseActivity
import com.dungnm.example.compose.ui.theme.MainAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    override val viewModel: CommonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView {
            HomeScreen().Screen(viewModel)
        }
    }
}

