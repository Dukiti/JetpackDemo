package com.dungnm.example.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.dungnm.example.compose.ui.GalleryScreen
import com.dungnm.example.compose.ui.HomeScreen
import com.dungnm.example.compose.ui.base.BaseActivity
import com.dungnm.example.compose.ui.theme.JetPackDemoTheme
import com.dungnm.example.compose.viewmodels.GalleryViewModel

class GalleryActivity : BaseActivity() {

    override var titleRes: Int = R.string.label_gallery

    private val viewModel by viewModels<GalleryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetPackDemoTheme {
                GalleryScreen(viewModel)
            }
        }
        viewModel.loadNextPage(this)
    }
}

