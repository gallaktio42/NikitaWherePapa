package com.example.session2.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.session2.model.data.Screens
import com.example.session2.ui.theme.screens.ComposePDFViewer

fun NavGraphBuilder.pdfGraph(navController: NavController) {
    navigation(startDestination = Screens.PDFScreenRoute.route, route = Screens.PdfRoute.route) {
        composable(route = Screens.PDFScreenRoute.route) {
            ComposePDFViewer(navController = navController)
        }
    }
}