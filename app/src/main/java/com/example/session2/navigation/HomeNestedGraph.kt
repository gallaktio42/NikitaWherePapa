package com.example.session2.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.session2.model.data.Screens
import com.example.session2.ui.theme.screens.Home

fun NavGraphBuilder.homeGraph(navController: NavController){
    navigation(startDestination = Screens.ScreenHomeRoute.route, route = Screens.AppRoute.route){
        composable(route = Screens.ScreenHomeRoute.route) {
           Home(navController)
        }
    }
}