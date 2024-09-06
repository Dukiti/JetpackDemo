package com.dungnm.example.compose.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.dungnm.example.compose.R
import com.dungnm.example.compose.core.base.BaseActivity
import com.dungnm.example.compose.core.navigation.register
import com.dungnm.example.compose.home.navigation.HomeRoute
import com.dungnm.example.compose.login.navigation.LoginRoute
import com.dungnm.example.compose.setting.nav.SettingRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val titleRes: Int = R.string.app_name

    override val viewModel: CommonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.e("12387190218", "onCreate: 1236198230129381-20", )
        try {
            val a : String? = null
            checkNotNull(a)
        }catch (e: Exception) {
            e.printStackTrace()
        }
        setContentView {
            register(LoginRoute())
            register(HomeRoute())
            register(SettingRoute())
        }
    }

}

