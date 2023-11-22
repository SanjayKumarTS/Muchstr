package com.example.munchstr.ui.screens.userProfile


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.munchstr.R
import com.example.munchstr.model.User
import com.example.munchstr.ui.components.AppGlideSubcomposition
import com.example.munchstr.ui.components.SavedCards
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.viewModel.RecipeViewModel
import com.example.munchstr.viewModel.SignInViewModel


@Composable
fun UserProfile(navController: NavController, signInViewModel:
SignInViewModel, recipeViewModel:RecipeViewModel) {
    val userInfo by signInViewModel.userData.collectAsState()
    LazyColumn {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (userInfo != null) {
                        ProfileContent(navController = navController, userInfo = userInfo!!)
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(top = 100.dp)
                            )
                        }
                    }
                    ActionButtonsRow(navController)
                }
            }
        }

//        items(recipeViewModel.recipes) { recipe ->
//            SavedCards(recipe = recipe) {
//            }
//        }
    }
}


@Composable
fun ProfileContent(
    userInfo: User,
    navController: NavController
)  {
    println("Name: $userInfo")
    println("Name: $userInfo")
    println("Name: $userInfo")
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val dynamicSize = with(LocalDensity.current) { constraints.maxWidth.toDp() * 0.13f }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .clip(CircleShape)
                .clickable {
                    navController.navigate(NavigationRoutes.EDIT_PROFILE)
                }) {
                userInfo.photoURL?.let {
                    AppGlideSubcomposition(
                        imageUri = it,
                        modifier = Modifier.clipToBounds()
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.2f))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp, end = 10.dp)
            ) {

                Text(
                        text = userInfo.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                userInfo.bio?.let {
                    Text(
                        text = it,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.bookmarks),
                        contentDescription = "Collections",
                        modifier = Modifier
                            .size(dynamicSize)
                            .clickable {
                                navController.navigate(NavigationRoutes.BOOKMARKS)
                            }
                    )

                Text(
                    text = "Collections",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }

        }


    }
}


@Composable
fun ActionButtonsRow(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ActionButtonColumn(buttonText = "Followers", count = "23", onClickAction = {}, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.weight(0.2f))

        ActionButtonColumn(buttonText = "Following", count = "41", onClickAction = {}, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.weight(0.2f))

        ActionButtonColumn(buttonText = "Edit Profile",onClickAction = {
            navController.navigate(NavigationRoutes.EDIT_PROFILE) },
             modifier = Modifier.weight(1f))
    }
}


@Composable
fun ActionButtonColumn(
    buttonText: String,
    count: String = "",
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val minimumHeight = 28.dp


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Button(
            onClick = onClickAction,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .height(minimumHeight),
            shape = RoundedCornerShape(4.dp),
            contentPadding = PaddingValues(
                start = 10.dp,
                end = 10.dp,
                top = 4.dp,
                bottom = 4.dp
            )
        ) {
            Text(
                text = buttonText,
                color = Color.White,
                style = MaterialTheme.typography.labelSmall
            )
        }

        if (count.isNotEmpty()) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = count,
                style = MaterialTheme.typography.titleSmall
            )
        }

    }
}
