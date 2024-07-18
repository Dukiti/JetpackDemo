/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dungnm.example.compose.ui.common

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ContactSupport
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.dungnm.example.compose.base.BaseActivity
import com.dungnm.example.compose.R
import com.dungnm.example.compose.ui.activity.setting.SettingActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(
    onUpClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val crrContext = LocalContext.current
    val titleRes = (crrContext as? BaseActivity)?.titleRes ?: R.string.app_name
    val defaultCLick: (() -> Unit) = {
        (crrContext as? Activity)?.finish()
    }
    CenterAlignedTopAppBar(title = {
        Text(stringResource(id = titleRes))
    }, modifier = modifier.statusBarsPadding(), navigationIcon = {
        IconButton(onClick = onUpClick ?: defaultCLick) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
            )
        }
    }, actions = {
        IconButton(onClick = {
            val intent = Intent(crrContext, SettingActivity::class.java)
            crrContext.startActivity(intent)
        }) {
            Icon(
                Icons.Rounded.Settings, contentDescription = null
            )
        }
    })
}