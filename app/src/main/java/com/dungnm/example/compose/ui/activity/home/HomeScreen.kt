package com.dungnm.example.compose.ui.activity.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.dungnm.example.compose.R
import com.dungnm.example.compose.ui.activity.CommonViewModel
import com.dungnm.example.compose.ui.activity.gallery.GalleryActivity
import com.dungnm.example.compose.ui.activity.login.LoginActivity
import com.dungnm.example.compose.ui.activity.search.SearchActivity
import com.dungnm.example.compose.ui.base.BaseScreen
import com.dungnm.example.compose.ui.theme.MainAppTheme

class HomeScreen : BaseScreen<CommonViewModel>() {

    @Composable
    override fun Screen(viewModel: CommonViewModel) {
        super.Screen(viewModel)
    }

    @Composable
    override fun ContentView(viewModel: CommonViewModel, innerPadding: PaddingValues) {
        val context = LocalContext.current
        val columnModifier = Modifier
            .padding(innerPadding)
            .padding(0.dp, 160.dp, 0.dp, 0.dp)
            .fillMaxSize()
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                openGallery(context)
            } else {
                Toast.makeText(context, "isGranted is false", Toast.LENGTH_SHORT).show()
            }
        }

        Column(
            columnModifier, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val modifier = Modifier
                .padding(12.dp)
                .width(200.dp)
            Button(modifier = modifier, onClick = {
                openGallery(context, launcher)
            }) {
                Text(text = stringResource(id = R.string.label_gallery))
            }
            Button(modifier = modifier, onClick = { openLoginForm(context) }) {
                Text(text = stringResource(id = R.string.label_login))
            }
            Button(modifier = modifier, onClick = { openSearch(context) }) {
                Text(text = stringResource(id = R.string.label_search))
            }
        }
    }

    private fun openGallery(
        context: Context, launcher: ManagedActivityResultLauncher<String, Boolean>? = null
    ) {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, permission) -> {
                context.startActivity(Intent(context, GalleryActivity::class.java))
            }

            else -> {
                launcher?.launch(permission)
            }
        }
    }

    private fun openLoginForm(context: Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }

    private fun openSearch(context: Context) {
        context.startActivity(Intent(context, SearchActivity::class.java))
    }

    @Preview(showBackground = true, device = Devices.PIXEL_2)
    @Composable
    fun Preview() {
        MainAppTheme {
            Screen(hiltViewModel())
        }
    }
}