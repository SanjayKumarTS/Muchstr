package com.example.munchstr.ui.screens.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.viewModel.SignInViewModel

@Composable
fun Login(
    signInViewModel: SignInViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        Log.d("Login", "ActivityResult: ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            signInViewModel.onSignInResult(data)
        }
    }

    Button(onClick = {
        Log.d("Login", "Login button clicked")
        signInViewModel.signIn(activityResultLauncher)
    }) {
        Text("Login")
    }

    val signInState = signInViewModel.state.collectAsState()
    if (signInState.value.isSignInSuccessful) {
        LaunchedEffect(signInState.value) {
            Log.d("Login","Navigating to Home Route")
            navController.navigate(NavigationRoutes.HOME_ROUTE) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    } else if (signInState.value.signInError != null) {
        Text("Error: ${signInState.value.signInError}")
    }
}