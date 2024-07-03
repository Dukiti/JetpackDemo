package com.dungnm.example.compose.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dungnm.example.compose.GalleryActivity
import com.dungnm.example.compose.ui.common.TopBar
import com.dungnm.example.compose.ui.theme.JetPackDemoTheme

@Composable
fun HomeScreen() {
    val currentActivity = LocalContext.current as? Activity
    val context = LocalContext.current
    Scaffold(topBar = {
        TopBar()
    }) { innerPadding ->
        val columnModifier = Modifier
            .padding(innerPadding)
            .padding(0.dp, 160.dp, 0.dp, 0.dp)
            .fillMaxSize()

        Column(
            columnModifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val modifier = Modifier
                .padding(12.dp)
                .width(200.dp)
            Button(modifier = modifier, onClick = {
                openGallery(context)
            }) {
                Text(text = "Gallery")
            }
            Button(modifier = modifier, onClick = { openGallery(context) }) {
                Text(text = "Scan QR")
            }
            Button(modifier = modifier, onClick = { openGallery(context) }) {
                Text(text = "Chat")
            }
        }
    }
}

private fun openGallery(context: Context) {
    context.startActivity(Intent(context, GalleryActivity::class.java))
}

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun GreetingPreview() {
    JetPackDemoTheme {
        HomeScreen()
    }
}