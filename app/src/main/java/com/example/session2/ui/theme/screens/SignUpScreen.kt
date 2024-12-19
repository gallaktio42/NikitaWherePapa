package com.example.session2.ui.theme.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.session2.R
import com.example.session2.model.data.Screens
import com.example.session2.model.models.SignUpEventState
import com.example.session2.ui.theme.BlackForIcon
import com.example.session2.ui.theme.GrayForTextFields
import com.example.session2.ui.theme.Primary
import com.example.session2.ui.theme.Secondary
import com.example.session2.ui.theme.YellowForButton
import com.example.session2.viewmodels.SignUpViewmodel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewmodel = viewModel()
) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        TitleText()
        Column {
            Text(text = "Full Name", style = MaterialTheme.typography.bodyMedium)
            OutlinedTextField(
                value = viewModel.state.name,
                onValueChange = { viewModel.onChange(SignUpEventState.UpdateName(it)) },

                Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally),
                placeholder = {
                    Text(
                        "Ivanov Ivan",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                textStyle = MaterialTheme.typography.labelMedium,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = GrayForTextFields, focusedBorderColor = Primary
                )
            )
            Spacer(modifier = Modifier.height(23.dp))
            Text(text = "Phone Number", style = MaterialTheme.typography.bodyMedium)
            OutlinedTextField(
                value = viewModel.state.phone,
                onValueChange = {
                    viewModel.onChange(SignUpEventState.UpdatePhone(it))
                },
                Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally),

                isError = !viewModel.isPhoneValidNumber(),
                placeholder = {
                    Text(
                        "+7(999)999-99-99",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },

                colors = OutlinedTextFieldDefaults.colors(
                    errorTextColor = Color.Red,
                    errorBorderColor = Color.Red,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = if (viewModel.isPhoneValidNumber()) Primary else Color.Red,
                    unfocusedBorderColor = if (viewModel.isPhoneValidNumber()) GrayForTextFields else Color.Red,

                    ),
                textStyle = MaterialTheme.typography.labelMedium,
            )
            Spacer(modifier = Modifier.height(23.dp))
            Text(text = "Email", style = MaterialTheme.typography.bodyMedium)
            OutlinedTextField(
                value = viewModel.state.email,
                onValueChange = {
                    viewModel.onChange(SignUpEventState.UpdateEmail(it))
                },
                Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally),
                isError = !viewModel.isValidEmail() && viewModel.state.email.isNotEmpty(),
                placeholder = {
                    Text(
                        "***********@mail.com",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },

                colors = OutlinedTextFieldDefaults.colors(
                    errorTextColor = Color.Red,
                    errorBorderColor = Color.Red,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedBorderColor = GrayForTextFields,
                    focusedBorderColor = Primary,
                ),
                textStyle = MaterialTheme.typography.labelMedium,
            )

            Spacer(modifier = Modifier.height(23.dp))

            Text(text = "Password", style = MaterialTheme.typography.bodyMedium)
            OutlinedTextField(
                value = viewModel.state.password,
                onValueChange = {
                    viewModel.onChange(SignUpEventState.UpdatePass(it))
                },
                Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally),
                placeholder = {
                    Text(
                        "**********",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                textStyle = MaterialTheme.typography.labelMedium,
                visualTransformation = if (viewModel.visibility) VisualTransformation.None else PasswordVisualTransformation(
                    mask = '*'
                ),
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
                            Icon(Icons.Outlined.VisibilityOff, contentDescription = "On")
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = GrayForTextFields, focusedBorderColor = Primary
                )
            )

            Spacer(modifier = Modifier.height(23.dp))

            Text(text = "Confirm password", style = MaterialTheme.typography.bodyMedium)
            OutlinedTextField(
                value = viewModel.state.passwordV,
                onValueChange = {
                    viewModel.onChange(SignUpEventState.UpdatePassV(it))
                },
                Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally),
                placeholder = {
                    Text(
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
                        if (viewModel.visibilityV) {
                            Icon(Icons.Outlined.Visibility, contentDescription = "Off")
                        } else {
                            Icon(Icons.Outlined.VisibilityOff, contentDescription = "On")
                        }
                    }
                },
                isError = if (viewModel.state.password == viewModel.state.passwordV) false else true,
                textStyle = MaterialTheme.typography.labelMedium,
                visualTransformation = if (viewModel.visibilityV) VisualTransformation.None else PasswordVisualTransformation(
                    mask = '*'
                ),
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
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.size(24.dp)) {
                Checkbox(
                    checked = viewModel.state.check,
                    onCheckedChange = { viewModel.onChange(SignUpEventState.UpdateCheck(it)) },
                    colors = CheckboxDefaults.colors(uncheckedColor = GrayForTextFields)
                )
            }
            Spacer(Modifier.width(4.dp))
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = "By ticking this box, you agree to our",
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "Terms and conditions and private policy",
                    Modifier
                        .fillMaxWidth()
                        .clickable {navController.navigate(Screens.PDFScreenRoute.route)},
                    textAlign = TextAlign.Center,
                    color = YellowForButton,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        SingUp(
            enabled = viewModel.enabled(),
            onSignInClick = { navController.navigate(Screens.ScreenLoginRoute.route){
                popUpTo(navController.graph.findStartDestination().id)
            } },
            onClick = {
                viewModel.signUp(
                    context = context,
                    nameUser = viewModel.state.name,
                    numberUser = viewModel.state.phone,
                    emailUser = viewModel.state.email,
                    passwordUser = viewModel.state.passwordV,
                    navController
                )
            },
            onSignInGoogle = { viewModel.signInWithGoogle(context, navController) }
        )
    }
}

@Composable
private fun TitleText(modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Create an account",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start
        )
        Spacer(modifier.height(8.dp))
        Text(
            text = "Complete the sign up process to get started",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun SingUp(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    onSignInClick: () -> Unit,
    onSignInGoogle: () -> Unit
) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClick,
            enabled = enabled,
            shape = RoundedCornerShape(4.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(46.dp)
        ) {
            Text(text = "Sign Up")
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account?", style = MaterialTheme.typography.bodyMedium)
            Box(Modifier.absoluteOffset((-11).dp)) {
                TextButton(onClick = onSignInClick) {
                    Text(text = "Sign in", style = MaterialTheme.typography.labelSmall)
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun Preview() {
    SignUpScreen(navController = rememberNavController())
}
