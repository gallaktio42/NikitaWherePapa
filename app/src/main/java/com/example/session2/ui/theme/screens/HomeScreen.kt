package com.example.session2.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.session2.viewmodels.HomeViewmodel

@Composable
fun Home(navController: NavController){
    val viewmodel: HomeViewmodel = viewModel()
    val context = LocalContext.current
    Column (Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Home")
        Button(onClick = {viewmodel.logOut(context, navController)}) {
            Text("Log Out")
        }
        Button(onClick = {}) {
            Text("get info")
        }
    }
}