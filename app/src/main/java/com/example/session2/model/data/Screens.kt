package com.example.session2.model.data

sealed class Screens(val route: String){
    object ScreenRegisterRoute : Screens(route = "Register")
    object ScreenLoginRoute : Screens(route = "Login")
    object ScreenForgetPassRoute : Screens(route = "ForgetPass")
    object ScreenSetPass : Screens(route = "SetPass")
    object ScreenNewPass : Screens(route = "NewPass")

    object ScreenHomeRoute : Screens(route = "Home")

    object PDFScreenRoute: Screens(route = "PDF")

    object AuthRoute : Screens(route = "Auth")
    object AppRoute : Screens(route = "App")
    object PdfRoute: Screens(route = "Pdf")
}