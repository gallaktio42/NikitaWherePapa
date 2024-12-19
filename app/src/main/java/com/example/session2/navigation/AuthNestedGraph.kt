package com.example.session2.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.session2.model.data.Screens
import com.example.session2.ui.theme.screens.ForgotPasswordScreen
import com.example.session2.ui.theme.screens.NewPasswordScreen
import com.example.session2.ui.theme.screens.OTPVerifyScreen
import com.example.session2.ui.theme.screens.SignInScreen
import com.example.session2.ui.theme.screens.SignUpScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = Screens.ScreenLoginRoute.route, route = Screens.AuthRoute.route) {
        composable(route = Screens.ScreenLoginRoute.route) {
            SignInScreen(navController = navController)
        }
        composable(route = Screens.ScreenRegisterRoute.route) {
            SignUpScreen(navController = navController)
        }
        composable(route = Screens.ScreenForgetPassRoute.route) {
            ForgotPasswordScreen(navController = navController)
        }
        composable(route = "${Screens.ScreenSetPass.route}/{email}") {
            val email = it.arguments?.getString("email") ?: ""
            OTPVerifyScreen(email = email, navController = navController)
        }
        composable(route = Screens.ScreenNewPass.route) {
            NewPasswordScreen(navController = navController)
        }
    }
}