package com.example.munchstr.ui.screens.login

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.munchstr.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen()
{
    val primaryColor = MaterialTheme.colorScheme.primary
    var showProgress by remember { mutableStateOf(true) }
    val customAnimationSpec = remember {
        tween<Float>(durationMillis = 800)
    }

    LaunchedEffect(Unit) {
        val delayDuration = 2000L
        launch {
            delay(delayDuration)
            showProgress = false
        }
    }

    Surface(
        color = primaryColor,
        modifier = Modifier.fillMaxSize()
    )
    {
        Crossfade(targetState = showProgress,
            label = "animation",
            animationSpec = customAnimationSpec) { isShowingProgress ->
            if (isShowingProgress) {
                LoadingScreen()
            } else {
                SignUpScreen()
            }
        }
    }
}

@Composable
fun LoadingScreen()
{
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    Column(modifier = Modifier
        .fillMaxSize())
    {

        Spacer(modifier = Modifier.fillMaxSize(1f/3f))
        Image(painter = painterResource(id = R.drawable.munchstr_icon),
            contentDescription = "munchstricon",
            contentScale = ContentScale.Crop,
            modifier= Modifier
                .align(Alignment.CenterHorizontally)
                .width(110.dp)
                .height(110.dp)
                .clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Snap Share and Savor!",
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 25.sp,
            color = onPrimaryColor,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(50.dp))
        CircularProgressIndicator(
            color = onPrimaryColor,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(30.dp)
        )
    }
}

@Composable
fun SignUpScreen()
{
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    Column(modifier = Modifier
        .fillMaxSize())
    {
        Spacer(modifier = Modifier.fillMaxSize(1f/3f))
        Image(painter = painterResource(id = R.drawable.munchstr_icon),
            contentDescription = "munchstricon",
            contentScale = ContentScale.Crop,
            modifier= Modifier
                .align(Alignment.CenterHorizontally)
                .width(100.dp)
                .height(100.dp)
                .clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Start your culinary quest today!",
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 23.sp,
            color = onPrimaryColor,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.fillMaxSize(1f/3f))

        Button(
            onClick = {
                      //Google Signin navigation
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
}

@Preview
@Composable
fun LoginScreenPreview()
{
    LoginScreen()
}