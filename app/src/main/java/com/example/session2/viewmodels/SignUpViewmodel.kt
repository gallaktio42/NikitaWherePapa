package com.example.session2.viewmodels


import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.session2.model.data.Screens
import com.example.session2.model.models.SignUpEventState
import com.example.session2.model.models.SignUpScreenState
import com.example.session2.model.data.SignUpUserTable
import com.example.session2.model.network.Supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class SignUpViewmodel : ViewModel() {
    var state by mutableStateOf(SignUpScreenState())
        private set

    var visibility by mutableStateOf(false)

    var visibilityV by mutableStateOf(false)


    fun onChange(event: SignUpEventState) {
        state = when (event) {
            is SignUpEventState.UpdateName -> state.copy(name = event.newName)
            is SignUpEventState.UpdatePhone -> state.copy(phone = event.newPhone)
            is SignUpEventState.UpdateEmail -> state.copy(email = event.newEmail)
            is SignUpEventState.UpdatePass -> state.copy(password = event.newPass)
            is SignUpEventState.UpdatePassV -> state.copy(passwordV = event.newPassV)
            is SignUpEventState.UpdateCheck -> state.copy(check = event.newCheck)
        }
    }

    fun isPhoneValidNumber(): Boolean {
        return state.phone.all { it.isDigit() }
    }

    fun isValidEmail(): Boolean {
        return state.email.all {
            it.isLowerCase() || it.isDigit() || it in listOf(
                '@',
                '.',
                '_',
                '-'
            )
        } && (state.email.contains('@') && state.email.contains('.') && state.email.length > 5)
    }

    fun enabled(): Boolean {
        return state.check && state.name.isNotEmpty() && state.phone.isNotEmpty() && state.email.isNotEmpty() && state.password.isNotEmpty()
                && state.passwordV.isNotEmpty() && state.password == state.passwordV && isPhoneValidNumber() && isValidEmail()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun signUp(
        context: Context,
        nameUser: String,
        numberUser: String,
        emailUser: String,
        passwordUser: String,
        navController: NavController
    ) {
        viewModelScope.launch {
            try {
                supabase.auth.signUpWith(Email) {
                    email = emailUser
                    password = passwordUser
                }
                val user = supabase.auth.retrieveUserForCurrentSession()
                val identities = user.identities

                if (!identities.isNullOrEmpty()) {
                    val newUser = SignUpUserTable(
                        id = identities.first().userId,
                        nameUser,
                        numberUser,
                        emailUser,
                        createdAt = identities.first().createdAt
                    )
                    supabase.from("SignUpUsers")
                        .insert(newUser)
                }
                Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
                supabase.auth.signOut()
                navController.navigate(Screens.ScreenLoginRoute.route){
                    popUpTo(navController.graph.findStartDestination().id)
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*fun signInWithGoogle(
        context: Context,
        navController: NavController
    ) {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId("64703246246-q19fomea6rjhjs2se6seh844rcs1j8gd.apps.googleusercontent.com")
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(result.credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                supabase.auth.signInWith(IDToken) {
                    idToken = googleIdToken
                    provider = Google
                }
                // Handle successful sign-in
                Toast.makeText(context, "Sign In With Google!", Toast.LENGTH_SHORT).show()
                navController.navigate(Screens.ScreenHomeRoute.route)
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }

        }
    }*/

    fun signInWithGoogle(
        context: Context,
        navController: NavController
    ){
        viewModelScope.launch {
            try {
                supabase.auth.signInWith(Google)
                Toast.makeText(context, "SignIn", Toast.LENGTH_SHORT).show()
                navController.navigate(Screens.ScreenHomeRoute.route){
                    popUpTo(navController.graph.findStartDestination().id)
                    restoreState = true
                }
            }catch (e: Exception){
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }

        }
    }
}