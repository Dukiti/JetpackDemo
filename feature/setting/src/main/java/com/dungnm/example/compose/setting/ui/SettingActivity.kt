package com.dungnm.example.compose.setting.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.dungnm.example.compose.core.base.BaseActivity
import com.dungnm.example.compose.login.R
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