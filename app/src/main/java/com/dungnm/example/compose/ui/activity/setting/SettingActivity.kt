package com.dungnm.example.compose.ui.activity.setting

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.dungnm.example.compose.R
import com.dungnm.example.compose.ui.base.BaseActivity
import com.dungnm.example.compose.ui.theme.MainAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity() {

    override val viewModel: SettingViewModel by viewModels()

    override var titleRes: Int = R.string.label_setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainAppTheme {
                SettingScreen().Screen(viewModel)
            }
        }
    }
}