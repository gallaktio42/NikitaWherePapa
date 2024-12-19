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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.session2.R
import com.example.session2.model.data.Screens
import com.example.session2.model.models.SignInEventState
import com.example.session2.ui.theme.BlackForIcon
import com.example.session2.ui.theme.GrayForText
import com.example.session2.ui.theme.GrayForTextFields
import com.example.session2.ui.theme.Primary
import com.example.session2.ui.theme.Secondary
import com.example.session2.viewmodels.SignInViewmodel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignInViewmodel = viewModel()
) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.SpaceEvenly

    ) {
        Column {
            TitleText(modifier = modifier)
            Spacer(modifier = Modifier.height(23.dp))
            Text(text = "Email Address", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.state.email,
                onValueChange = {
                    viewModel.onChange(SignInEventState.UpdateEmail(it))
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "***********@mail.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = GrayForText
                    )
                },
                isError = !viewModel.isValidEmail() && viewModel.state.email.isNotEmpty(),
                textStyle = MaterialTheme.typography.labelMedium,
                colors = OutlinedTextFieldDefaults.colors(
                    errorTextColor = Color.Red,
                    errorBorderColor = Color.Red,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedBorderColor = GrayForTextFields,
                    focusedBorderColor = Primary,
                ),

                )
            Spacer(modifier = Modifier.height(23.dp))
            Text(text = "Password", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.state.passwordV,
                onValueChange = {
                    viewModel.onChange(SignInEventState.UpdatePassV(it))
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "**********",
                        style = MaterialTheme.typography.bodyMedium,
                        color = GrayForText
                    )
                },
                textStyle = MaterialTheme.typography.labelMedium,
                trailingIcon = {
                    IconButton(
                        onClick = { viewModel.visibility = !viewModel.visibility },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = BlackForIcon
                        )
                    ) {
                        if (viewModel.visibility) {
                            Icon(Icons.Outlined.Visibility, contentDescription = "Off")
                        }
                        else{
                            Icon(Icons.Outlined.VisibilityOff, contentDescription = "Off")
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    errorTextColor = Color.Red,
                    errorBorderColor = Color.Red,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedBorderColor = GrayForTextFields,
                    focusedBorderColor = Primary,
                ),
                visualTransformation = if (viewModel.visibility) VisualTransformation.None else PasswordVisualTransformation(mask = '*')
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(25.dp)) {
                    Checkbox(
                        checked = viewModel.state.check,
                        onCheckedChange = { viewModel.onChange(SignInEventState.UpdateCheck(it)) },
                        modifier = Modifier.padding(0.dp),
                        colors = CheckboxDefaults.colors(uncheckedColor = GrayForTextFields)
                    )
                }
                Spacer(Modifier.width(4.dp))
                Text("Remember Password", style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.weight(1f))
                TextButton(onClick = { navController.navigate(Screens.ScreenForgetPassRoute.route) }) { Text("Forgot Password?") }
            }

        }
        Buttons(
            enabled = viewModel.enabled(),
            onSignUpClick = { navController.navigate(Screens.ScreenRegisterRoute.route) },
            onClick = {
                viewModel.signIn(context, viewModel.state.email, viewModel.state.passwordV, navController)
            },
            modifier = modifier,
            onSignInGoogle = {viewModel.signInWithGoogle(context, navController)}
        )
    }
}


@Composable
private fun TitleText(modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.Center) {
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start
        )
        Spacer(modifier.height(8.dp))
        Text(
            text = "Fill in your email and password to continue",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun Buttons(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onSignInGoogle: () -> Unit
) {
    Column(
        modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            enabled = enabled
        ) {
            Text("Log In")
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account?", style = MaterialTheme.typography.bodyMedium)
            Box(modifier.absoluteOffset((-11).dp)) {
                TextButton(onClick = onSignUpClick) {
                    Text(text = "Sign Up", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "or sign in using", style = MaterialTheme.typography.bodyMedium)
            IconButton(onClick = onSignInGoogle) {
                Icon(
                    painter = painterResource(id = R.drawable.facebook_google__apple),
                    contentDescription = "google",
                    tint = Secondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SignInScreen(navController = rememberNavController())
}
