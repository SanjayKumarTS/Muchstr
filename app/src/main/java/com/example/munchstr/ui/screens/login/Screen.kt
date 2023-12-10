package com.example.munchstr.ui.screens.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.munchstr.R
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.utils.ConnectivityObserver
import com.example.munchstr.utils.NetworkStatusHolder
import com.example.munchstr.viewModel.SignInViewModel

@Composable
fun Login(
    signInViewModel: SignInViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val networkStatus by signInViewModel.networkStatus.collectAsState()

    LaunchedEffect(Unit) {
        val cachedUser = signInViewModel.checkCachedUserData()
        if (cachedUser != null) {
            Log.d("Login", "Cached user found, navigating to Home Route")
            if(networkStatus == ConnectivityObserver.Status.Unavailable ||
                networkStatus == ConnectivityObserver.Status.Lost) {
                navController.navigate(NavigationRoutes.BOOKMARKS) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
            else{
                navController.navigate(NavigationRoutes.HOME_ROUTE) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        }
    }

    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        Log.d("Login", "ActivityResult: ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                Log.d("Login", data.extras.toString())
            }
            signInViewModel.onSignInResult(data)
        }
    }

    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.primary)
    )
    {
        Spacer(modifier = Modifier.fillMaxSize(1f / 5f))
        Image(
            painter = painterResource(id = R.drawable.munchstr_icon),
            contentDescription = "munchstricon",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp)
                .height(200.dp)
                .clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Start your culinary quest today!",
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp,
            color = onPrimaryColor,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.fillMaxSize(1f / 3f))
        Button(
            onClick = {
                Log.d("Login", "Login button clicked")
                signInViewModel.signIn(activityResultLauncher)
            },
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(100.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = onPrimaryColor
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_icon),
                contentDescription = "",
                modifier= Modifier
                    .width(18.dp)
                    .height(18.dp)
            )
            Text(text = "Sign up with Google",
                modifier = Modifier.padding(horizontal =10.dp),
                fontSize = 18.sp,
                color = Color.Gray)
        }
    }

    val signInState = signInViewModel.state.collectAsState()
    if (signInState.value.isSignInSuccessful) {
        LaunchedEffect(signInState.value) {
            if(networkStatus == ConnectivityObserver.Status.Unavailable ||
                networkStatus == ConnectivityObserver.Status.Lost) {
                navController.navigate(NavigationRoutes.BOOKMARKS) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
            else{
                Log.d("Login", "Sign-in successful, navigating to Home Route")
                navController.navigate(NavigationRoutes.HOME_ROUTE) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        }
    } else if (signInState.value.signInError != null) {
        Text("Error: ${signInState.value.signInError}")
    }
}