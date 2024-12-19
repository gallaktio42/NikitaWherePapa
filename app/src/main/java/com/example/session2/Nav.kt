package com.example.session2

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.session2.navigation.authGraph
import com.example.session2.model.data.Screens
import com.example.session2.model.network.Supabase.supabase
import com.example.session2.navigation.homeGraph
import com.example.session2.navigation.pdfGraph
import com.example.session2.viewmodels.HomeViewmodel
import io.github.jan.supabase.auth.auth

/*@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Nav(viewModel: HomeViewmodel = viewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.AuthRoute.route) {
        if (viewModel.getInfo() == null){
            authGraph(navController)
            pdfGraph(navController)
        }else{
            homeGraph(navController)
        }
    }
}*/

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Nav(viewModel: HomeViewmodel = viewModel()) {
    val navController = rememberNavController()

    if (viewModel.sessionState == null) {
        NavHost(
            navController = navController,
            startDestination = Screens.AuthRoute.route
        ) {
            authGraph(navController)
            pdfGraph(navController)
            homeGraph(navController)
        }
    } else {
        NavHost(navController = navController, startDestination = Screens.AppRoute.route) {
            homeGraph(navController)
        }
    }
}