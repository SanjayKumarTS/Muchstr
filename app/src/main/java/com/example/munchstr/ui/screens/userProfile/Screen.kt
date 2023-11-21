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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.munchstr.R
import com.example.munchstr.ui.components.SavedCards
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.viewModel.RecipeViewModel


@Composable
fun UserProfile(navController: NavController, recipeViewModel: RecipeViewModel) {
    var name by remember { mutableStateOf("Jitu Vashista") }
    var bio by remember { mutableStateOf("Documenting my food journey, one recipe at a time.") }


    LaunchedEffect(Unit) {
        recipeViewModel.loadRecipes()
    }

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

                    ProfileContent(
                        name = name,
                        bio = bio,navController
                    )
                    ActionButtonsRow(navController)
                }
            }
        }

        items(recipeViewModel.recipes) { recipe ->
            SavedCards(recipe = recipe) {
            }
        }
    }
}


@Composable
fun ProfileContent(

    name: String,

    bio: String,

    navController: NavController
)  {
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


            Image(
                painter = painterResource(id = R.drawable.dall_e_2023_10_19_10_14_13___photo_profile_pic_of_a_young_man_with_tan_skin__spiky_black_hair__and_hazel_eyes__he_has_a_playful_smirk_and_is_wearing_a_gray_t_shirt),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.3f)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .clipToBounds()

            )
            Spacer(modifier = Modifier.weight(0.2f))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp, end = 10.dp)
            ) {

                Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = bio,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )

            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Image(
                        painter = painterResource(id = R.drawable.bookmark),
                        contentDescription = "Collections",
                        modifier = Modifier.size(dynamicSize).
                                clickable {
                            navController.navigate(NavigationRoutes.COLLECTIONS)
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

        ActionButtonColumn(buttonText = "Edit Profile",onClickAction = { navController.navigate("editprofile") },
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
