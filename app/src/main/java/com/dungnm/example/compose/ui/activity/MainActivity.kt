package com.dungnm.example.compose.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.dungnm.example.compose.R
import com.dungnm.example.compose.core.base.BaseActivity
import com.dungnm.example.compose.core.navigation.register
import com.dungnm.example.compose.core.ui.theme.MainAppTheme
import com.dungnm.example.compose.home.navigation.HomeFeatureApi
import com.dungnm.example.compose.login.navigation.LoginFeatureApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val titleRes: Int = R.string.app_name

    override val viewModel: CommonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainAppTheme(builder = { navHostController ->
                register(LoginFeatureApi())
                register(HomeFeatureApi())
            })
        }
    }
}

