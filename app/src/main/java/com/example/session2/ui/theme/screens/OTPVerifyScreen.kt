package com.example.session2.ui.theme.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.session2.model.models.OTPVerifyEventState
import com.example.session2.ui.theme.BlackForIcon
import com.example.session2.ui.theme.Primary
import com.example.session2.viewmodels.OTPVerifyViewmodel

@Composable
fun OTPVerifyScreen(
    navController: NavController,
    email: String,
    viewModel: OTPVerifyViewmodel = viewModel()
) {
    val context = LocalContext.current
    val time by viewModel.time.collectAsState()
    val show by viewModel.show.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(33.dp, alignment = Alignment.CenterVertically),
    ) {
        TitleText()
        Spacer(Modifier.height(1.dp))
        /*OutlinedTextField(
            value = viewModel.state.token,
            onValueChange = { viewModel.onChange(OTPVerifyEventState.UpdateToken(it)) },
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
        )*/
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicTextField(
                value = viewModel.state.token,
                onValueChange = {
                    if (it.length <= 6) {
                        viewModel.onChange(OTPVerifyEventState.UpdateToken(it))
                    }
                },
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    repeat(6) { index ->
                        val number = when {
                            index >= viewModel.state.token.length -> ""
                            else -> viewModel.state.token[index]
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = number.toString()
                            )
                            Box(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(3.dp)
                                    .background(
                                        if (viewModel.state.token.isEmpty()) {
                                            BlackForIcon
                                        } else {
                                            Primary
                                        }
                                    )
                            )
                        }
                    }
                }
            }
        }

        ResendTimer(
            Modifier.align(alignment = Alignment.CenterHorizontally),
            time,
            show,
            onClick = {
                viewModel.otpMessage(context, email)
                viewModel.resetCountdown()
            })

        SetPassButton(
            onClick = {
                viewModel.verifyOTP(
                    context = context,
                    email = email,
                    token = viewModel.state.token,
                    navController
                )
            },
            enabled = viewModel.enabled()
        )
    }
}

@Composable
private fun TitleText() {
    Column(verticalArrangement = Arrangement.Center)
    {
        Text(
            "OTP Verification",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Enter the 6 digit numbers sent to your email",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun ResendTimer(
    modifier: Modifier = Modifier,
    time: Int,
    show: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!show && time >= 60) {
            Text(
                "If you didn’t receive code, resend after 1:00",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
            //это перенести к обычному тексту
            Spacer(Modifier.height(33.dp))
        } else if (!show && time < 60 && time >= 10) {
            Text(
                "If you didn’t receive code, resend after 0:$time",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(33.dp))

        } else if (!show && time < 10) {
            Text(
                "If you didn’t receive code, resend after 0:0$time",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(33.dp))
        } else {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    "If you didn’t receive code, ",
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )

                TextButton(onClick = onClick, Modifier.offset((-11).dp, (-16).dp)) {
                    Text(
                        "resend",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun SetPassButton(onClick: () -> Unit, enabled: Boolean) {
    Button(
        onClick = onClick,
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        enabled = enabled
    ) {
        Text("Set New Password")
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    OTPVerifyScreen(navController = rememberNavController(), email = "gallaktio@bk.ru")
}
