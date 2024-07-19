package com.dungnm.example.compose.ui.activity.setting

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import com.dungnm.example.compose.R
import com.dungnm.example.compose.core.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity() {

    override val viewModel: SettingViewModel by viewModels()

    override var titleRes: Int = R.string.label_setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView {
            SettingScreen().Screen(viewModel)
        }
    }
}