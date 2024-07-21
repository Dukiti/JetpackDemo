//package com.dungnm.example.compose.ui.activity.login
//
//import android.os.Bundle
//import androidx.activity.viewModels
//import com.dungnm.example.compose.R
//import com.dungnm.example.compose.core.base.BaseActivity
//
//class LoginActivity : BaseActivity() {
//
//    override val viewModel: LoginViewModel by viewModels()
//    override var titleRes: Int = R.string.label_login
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContentView {
//            LoginScreen().Screen(viewModel = viewModel)
//        }
//    }
//}