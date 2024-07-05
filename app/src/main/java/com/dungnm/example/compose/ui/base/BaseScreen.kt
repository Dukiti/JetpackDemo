package com.dungnm.example.compose.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dungnm.example.compose.ui.common.ToolBar
import com.dungnm.example.compose.ui.theme.JetPackDemoTheme
import com.dungnm.example.compose.viewmodels.CommonViewModel
import com.dungnm.example.compose.viewmodels.LoginViewModel

abstract class BaseScreen<T : BaseViewModel> {

    @Composable
    open fun Screen(viewModel: T) {
        Scaffold(
            topBar = { ToolBar() },
            content = { innerPadding ->
                LoadingDialog(viewModel = viewModel)
                ContentView(viewModel, innerPadding)
            }
        )
    }

    @Composable
    abstract fun ContentView(viewModel: T, innerPadding : PaddingValues)

    @Composable
    private fun LoadingDialog(viewModel: T){
        val showDialog by viewModel.isLoading.observeAsState()
        if (showDialog == true) {
            Dialog(
                onDismissRequest = { viewModel.isLoading.value = false },
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

}