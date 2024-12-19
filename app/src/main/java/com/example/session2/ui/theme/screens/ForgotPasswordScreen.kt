package com.example.session2.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.session2.model.models.ForgotPasswordEventState
import com.example.session2.model.data.Screens
import com.example.session2.ui.theme.GrayForText
import com.example.session2.ui.theme.GrayForTextFields
import com.example.session2.ui.theme.Primary
import com.example.session2.viewmodels.ForgotPasswordViewmodel

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewmodel = viewModel()
) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(
            45.dp, alignment = Alignment.CenterVertically
        )
    ) {
        Column {
            Title()
            Spacer(modifier = Modifier.height(45.dp))
            Text(text = "Email Address", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.state.email,
                onValueChange = {
                    viewModel.onChange(ForgotPasswordEventState.UpdateEmail(it))
                },
                Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.labelMedium,
                isError = !viewModel.isEmailValid() && viewModel.state.email.isNotEmpty(),
                placeholder = {
                    Text(
                        "***********@mail.com",
                        style = MaterialTheme.typography.labelMedium,
                        color = GrayForText
                    )
                },
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
        ButtonSendOTP(
            enabled = viewModel.enabledForForgotPass(),
            onSignInClick = {
                navController.navigate(
                    Screens.ScreenLoginRoute.route
                ){
                    popUpTo(navController.graph.findStartDestination().id)
                }
            },
            onSendOtpClick = {
                viewModel.otpMessage(
                    context,
                    viewModel.state.email,
                    navController
                )
            })
    }
}


@Composable
private fun Title(modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.Center) {
        Text(
            text = "Forgot Password",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start
        )
        Spacer(modifier.height(8.dp))
        Text(
            text = "Enter your email address",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun ButtonSendOTP(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onSignInClick: () -> Unit,
    onSendOtpClick: () -> Unit
) {
    Column(
        modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onSendOtpClick,
            modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            enabled = enabled
        ) {
            Text("Send OTP")
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Remember password Back to",
                style = MaterialTheme.typography.bodyMedium
            )
            Box(modifier = Modifier.absoluteOffset((-11).dp)) {
                TextButton(onClick = onSignInClick) {
                    Text(
                        text = "Sign in",
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ForgotPasswordScreen(navController = rememberNavController())
}

