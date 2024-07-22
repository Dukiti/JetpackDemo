package com.dungnm.example.compose.setting.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dungnm.example.compose.core.base.BaseActivity
import com.dungnm.example.compose.core.base.BaseScreen
import com.dungnm.example.compose.core.constants.Tags
import com.dungnm.example.compose.setting.R

class SettingScreen : BaseScreen<SettingViewModel>() {

    @Composable
    override fun ContentView(viewModel: SettingViewModel, innerPadding: PaddingValues) {
        val context = LocalContext.current
        val columnModifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        val languageSelected by viewModel.languageSelected.collectAsState()
        val themeSelected by viewModel.themeSelected.collectAsState()
        Column(modifier = columnModifier) {
            Text(
                text = stringResource(id = R.string.label_language),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = languageSelected == Tags.LANG_EN,
                    onClick = { viewModel.updateLanguage(Tags.LANG_EN, context) })
                Text(stringResource(id = R.string.label_en))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = languageSelected == Tags.LANG_VI,
                    onClick = { viewModel.updateLanguage(Tags.LANG_VI, context) })
                Text(stringResource(id = R.string.label_vi))
            }

            Text(
                text = stringResource(id = R.string.label_theme),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = themeSelected == Tags.THEME_LIGHT,
                    onClick = {
                        viewModel.updateTheme(Tags.THEME_LIGHT)
                        (context as? BaseActivity)?.updateTheme()
                    })
                Text(stringResource(id = R.string.label_light))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = themeSelected == Tags.THEME_DARK,
                    onClick = {
                        viewModel.updateTheme(Tags.THEME_DARK)
                        (context as? BaseActivity)?.updateTheme()
                    })
                Text(stringResource(id = R.string.label_dark))
            }
        }
    }
}