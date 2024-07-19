package com.dungnm.example.compose.login.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dungnm.example.compose.core.base.BaseScreen
import com.dungnm.example.compose.core.ui.theme.MainAppTheme

class LoginScreen : BaseScreen<LoginViewModel>() {

    @Composable
    override fun Screen(viewModel: LoginViewModel) {
        super.Screen(viewModel)
        val lifecycleOwner = LocalLifecycleOwner.current
        val context = LocalContext.current
        viewModel.loginResLiveData.observe(lifecycleOwner) { loginSuccess ->
            loginSuccess?.let {
                if (it) {
                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Login Fail", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    @Composable
    override fun ContentView(viewModel: LoginViewModel, innerPadding: PaddingValues) {
        val context = LocalContext.current
        val focusManager = LocalFocusManager.current
        Surface(modifier = Modifier.padding(innerPadding)) {
            var credentials by remember { mutableStateOf(Credentials()) }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    }
                    .padding(horizontal = 30.dp)
            ) {
                LoginField(
                    value = credentials.login,
                    onChange = { data -> credentials = credentials.copy(login = data) },
                    modifier = Modifier.fillMaxWidth()
                )
                PasswordField(
                    value = credentials.pwd,
                    onChange = { data -> credentials = credentials.copy(pwd = data) },
                    submit = {
                        onSubmit(viewModel, credentials, context)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                LabeledCheckbox(
                    label = "Remember Me",
                    onCheckChanged = {
                        credentials = credentials.copy(remember = !credentials.remember)
                    },
                    isChecked = credentials.remember
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        onSubmit(viewModel, credentials, context)
                    },
                    enabled = credentials.isNotEmpty(),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
            }

        }
    }


    private fun onSubmit(viewModel: LoginViewModel, credentials: Credentials, context: Context) {
        if (validData(credentials, context)) {
            viewModel.login(credentials.login, credentials.pwd)
        }
    }

    private fun validData(creds: Credentials, context: Context): Boolean {
        return creds.isNotEmpty()
    }

    data class Credentials(
        var login: String = "",
        var pwd: String = "",
        var remember: Boolean = false
    ) {
        fun isNotEmpty(): Boolean {
            return login.isNotEmpty() && pwd.isNotEmpty()
        }
    }


    @Composable
    fun LabeledCheckbox(
        label: String,
        onCheckChanged: () -> Unit,
        isChecked: Boolean
    ) {

        Row(
            Modifier
                .clickable(
                    onClick = onCheckChanged
                )
                .padding(4.dp)
        ) {
            Checkbox(checked = isChecked, onCheckedChange = null)
            Spacer(Modifier.size(6.dp))
            Text(label)
        }
    }

    @Composable
    fun LoginField(
        value: String,
        onChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        label: String = "Login",
        placeholder: String = "Enter your Login"
    ) {

        val focusManager = LocalFocusManager.current
        val leadingIcon = @Composable {
            Icon(
                Icons.Default.Person,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        TextField(
            value = value,
            onValueChange = onChange,
            modifier = modifier,
            leadingIcon = leadingIcon,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            placeholder = { Text(placeholder) },
            label = { Text(label) },
            singleLine = true,
            visualTransformation = VisualTransformation.None
        )
    }

    @Composable
    fun PasswordField(
        value: String,
        onChange: (String) -> Unit,
        submit: () -> Unit,
        modifier: Modifier = Modifier,
        label: String = "Password",
        placeholder: String = "Enter your Password"
    ) {

        var isPasswordVisible by remember { mutableStateOf(false) }

        val leadingIcon = @Composable {
            Icon(
                Icons.Default.Key,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        val trailingIcon = @Composable {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        TextField(
            value = value,
            onValueChange = onChange,
            modifier = modifier,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = { submit() }
            ),
            placeholder = { Text(placeholder) },
            label = { Text(label) },
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
    }


    @Preview(showBackground = true, device = Devices.PIXEL_2, showSystemUi = true)
    @Composable
    fun LoginFormPreview() {
        MainAppTheme {
            val loginViewModel: LoginViewModel = hiltViewModel()
            Screen(loginViewModel)
        }
    }

}