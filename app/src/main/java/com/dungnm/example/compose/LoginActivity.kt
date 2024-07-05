package com.dungnm.example.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.dungnm.example.compose.ui.LoginScreen
import com.dungnm.example.compose.ui.base.BaseActivity
import com.dungnm.example.compose.ui.theme.JetPackDemoTheme
import com.dungnm.example.compose.viewmodels.LoginViewModel

class LoginActivity : BaseActivity() {

    override val viewModel: LoginViewModel by viewModels()
    override var titleRes: Int = R.string.label_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetPackDemoTheme {
                LoginScreen().Screen(viewModel = viewModel)
            }
        }
    }
}