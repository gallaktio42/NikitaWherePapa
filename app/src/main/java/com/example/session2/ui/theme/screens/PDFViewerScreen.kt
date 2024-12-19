package com.example.session2.ui.theme.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.session2.R
import com.example.session2.common.PdfViewer
import com.example.session2.model.data.Screens

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ComposePDFViewer(navController: NavController) {
    var isLoading by remember { mutableStateOf(false) }
    var currentLoadingPage by remember { mutableStateOf<Int?>(null) }
    var pageCount by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mazda CX-8") },
                navigationIcon = {
                    IconButton({
                        navController.navigate(Screens.ScreenRegisterRoute.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                        }
                    }) { Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back to SignUP") }
                }
            )
        }
    ) {
        Box {
            PdfViewer(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                pdfResId = R.raw.mazda,
                loadingListener = { loading, currentPage, maxPage ->
                    isLoading = loading
                    if (currentPage != null) currentLoadingPage = currentPage
                    if (maxPage != null) pageCount = maxPage
                }
            )
            if (isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    LinearProgressIndicator(
                        progress = {
                            if (currentLoadingPage == null || pageCount == null) 0f
                            else currentLoadingPage!!.toFloat() / pageCount!!.toFloat()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 5.dp)
                            .padding(horizontal = 30.dp),
                        text = "${currentLoadingPage ?: "-"} pages loaded/${pageCount ?: "-"} total pages"
                    )
                }
            }
        }
    }
}