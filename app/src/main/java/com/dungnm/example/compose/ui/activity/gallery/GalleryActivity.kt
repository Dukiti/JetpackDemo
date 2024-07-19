package com.dungnm.example.compose.ui.activity.gallery

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.dungnm.example.compose.R
import com.dungnm.example.compose.core.base.BaseActivity
import com.dungnm.example.compose.ui.theme.MainAppTheme

class GalleryActivity : BaseActivity() {

    override var titleRes: Int = R.string.label_gallery

    override val viewModel by viewModels<GalleryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainAppTheme {
                GalleryScreen(viewModel)
            }
        }
        viewModel.loadNextPage(this)
    }
}

