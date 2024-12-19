package com.example.session2.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.session2.model.models.NewPasswordEventState
import com.example.session2.ui.theme.BlackForIcon
import com.example.session2.ui.theme.GrayForTextFields
import com.example.session2.ui.theme.Primary
import com.example.session2.viewmodels.NewPasswordViewmodel

@Composable
fun NewPasswordScreen(
    navController: NavController,
    viewModel: NewPasswordViewmodel = viewModel()
) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(55.dp, Alignment.CenterVertically)
    ) {
        TitleText()
        Column {
            androidx.compose.material3.Text(
                text = "Password", style = MaterialTheme.typography.bodyMedium
            )
            OutlinedTextField(
                value = viewModel.state.password,
                onValueChange = {
                    viewModel.onChange(NewPasswordEventState.UpdatePass(it))
                },
                Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally),
                placeholder = {
                    androidx.compose.material3.Text(
                        "**********",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                textStyle = MaterialTheme.typography.labelMedium,
                visualTransformation = if (viewModel.visibility) VisualTransformation.None else PasswordVisualTransformation(mask = '*'),
                trailingIcon = {
                    IconButton(
                        onClick = { viewModel.visibility = !viewModel.visibility },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = BlackForIcon
                        )
                    ) {
                        if (viewModel.visibility) {
                            Icon(Icons.Outlined.Visibility, contentDescription = "Off")
                        } else {
                            Icon(Icons.Outlined.VisibilityOff, contentDescription = "Off")
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = GrayForTextFields, focusedBorderColor = Primary
                )
            )
            Spacer(Modifier.height(23.dp))
            androidx.compose.material3.Text(
                text = "Confirm password", style = MaterialTheme.typography.bodyMedium
            )
            OutlinedTextField(
                value = viewModel.state.passwordV,
                onValueChange = {
                    viewModel.onChange(NewPasswordEventState.UpdatePassV(it))
                },
                Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally),
                placeholder = {
                    androidx.compose.material3.Text(
                        "**********",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { viewModel.visibilityV = !viewModel.visibilityV },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = BlackForIcon
                        )
                    ) {
                        if (viewModel.visibilityV)
                        {
                            Icon(Icons.Outlined.Visibility, contentDescription = "Off")
                        }
                        else {
                            Icon(Icons.Outlined.VisibilityOff, contentDescription = "Off")
                        }
                    }
                },
                isError = if (viewModel.state.password == viewModel.state.passwordV) false else true,
                textStyle = MaterialTheme.typography.labelMedium,
                visualTransformation = if (viewModel.visibilityV) VisualTransformation.None else PasswordVisualTransformation(mask = '*'),
                colors = OutlinedTextFieldDefaults.colors(
                    errorTextColor = Color.Red,
                    errorBorderColor = Color.Red,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedBorderColor = GrayForTextFields,
                    focusedBorderColor = Primary,
                )
            )
        }
        LogInButton(
            onClick = { viewModel.newPassword(context = context, navController) },
            enabled = viewModel.enabledForNewPass()
        )
    }
}

@Composable
private fun TitleText() {
    Column {
        androidx.compose.material3.Text(
            "New Password", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(8.dp))
        androidx.compose.material3.Text(
            "Enter new password",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun LogInButton(onClick: () -> Unit, enabled: Boolean) {
    Button(
        onClick = onClick,
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        enabled = enabled
    ) {
        androidx.compose.material3.Text("Log In")
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NewPasswordScreen(navController = rememberNavController())
}