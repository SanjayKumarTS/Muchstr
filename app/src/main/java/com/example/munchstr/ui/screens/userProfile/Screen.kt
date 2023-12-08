package com.example.munchstr.ui.screens.userProfile


import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.munchstr.R
import com.example.munchstr.model.ResponseFindRecipesForUserDTO
import com.example.munchstr.model.UserForProfile
import com.example.munchstr.ui.components.AnimatedPreloader
import com.example.munchstr.ui.components.AppCard
import com.example.munchstr.ui.components.AppGlideSubcomposition
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.ui.screens.home.handleCardClick
import com.example.munchstr.viewModel.FAndFViewModel
import com.example.munchstr.viewModel.RecipeViewModel
import com.example.munchstr.viewModel.SignInViewModel


@Composable
fun UserProfile(
    navController: NavController,
    signInViewModel: SignInViewModel,
    recipeViewModel:RecipeViewModel,
    selectedUserId: String="",
    fandFViewModel:FAndFViewModel
) {
    val user by signInViewModel.userData.collectAsState()
    val isCurrentUser =  selectedUserId == user?.uuid
    val isLoading by signInViewModel.isRefreshing

    LaunchedEffect(selectedUserId) {
        Log.d("UserProfileScreen","selectedUserId: $selectedUserId")
        signInViewModel.loadSelectedUserData(selectedUserId)
        recipeViewModel.loadRecipesOfUser(authorId = selectedUserId)
        fandFViewModel.updateFollowersAndFollowing(selectedUserId)
    }


    val selectedUser by signInViewModel.selectedUserData.collectAsState()
    val recipes = recipeViewModel.recipesForCards

    if (isLoading) {
        AnimatedPreloader()
    } else {
        selectedUser?.let { userInfo ->
            UserProfileContent(
                userInfo = userInfo,
                recipes = recipes,
                navController = navController,
                isCurrentUser = isCurrentUser,
                recipeViewModel = recipeViewModel,
                FAndFViewModel = fandFViewModel,
                selectedUserId = selectedUserId,
                signInViewModel = signInViewModel
            )
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserProfileContent(
    userInfo: UserForProfile,
    recipes: List<ResponseFindRecipesForUserDTO>,
    navController: NavController,
    isCurrentUser: Boolean,
    recipeViewModel: RecipeViewModel,
    FAndFViewModel: FAndFViewModel,
    selectedUserId: String,
    signInViewModel: SignInViewModel
){
    Column {
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
                ProfileContent(navController = navController, userInfo =
                userInfo!!, isCurrentUser = isCurrentUser)
                ActionButtonsRow(navController, isCurrentUser,userInfo,FAndFViewModel,selectedUserId,signInViewModel)
            }
        }
        LazyColumn {
            items(
                items = recipes,
                key = { item: ResponseFindRecipesForUserDTO -> item.uuid }
            ) { recipe ->
                recipe.userLiked?.let {
                    AppCard(
                        author = recipe.author,
                        recipe = recipe.recipe,
                        uuid = recipe.uuid,
                        recipeViewModel = recipeViewModel,
                        creationTime = recipe.recipe.creationTime,
                        isLiked = it,
                        showDelete = true,
                        onLikeClicked = { isLiked->
                            userInfo?.let { userInfo ->
                                if (isLiked) {
                                    userInfo.uuid?.let { it1 ->
                                        recipeViewModel.addLike(recipe.uuid,
                                            it1
                                        )
                                    }

                                } else {
                                    userInfo.uuid?.let { it1 ->
                                        recipeViewModel.removeLike(recipe.uuid,
                                            it1
                                        )
                                    }

                                }
                            }
                        },
                        onClick = {
                            recipeViewModel.updateSelectedRecipeAuthor(recipe.author)
                            handleCardClick(
                                navController,
                                recipeId = recipe.uuid,
                                likesCount = recipe.likesCount,
                                commentsCount = recipe.commentsCount,
                            )
                        },
                        navController = navController,
                        modifier = Modifier.animateItemPlacement
                            (
                            animationSpec = tween(
                                durationMillis =
                                600
                            )
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun ProfileContent(
    userInfo: UserForProfile,
    navController: NavController,
    isCurrentUser: Boolean
)  {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
    ) {
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
                userInfo.photo?.let {
                    AppGlideSubcomposition(
                        imageUri = it,
                        modifier = Modifier.clipToBounds()
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.075f))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp, end = 10.dp)
            ) {
                userInfo.name?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                userInfo.bio?.let {
                    Text(
                        text = it,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

            }
            if(isCurrentUser){
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.bookmarks),
                        contentDescription = "Collections",
                        modifier = Modifier
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
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ActionButtonsRow(
    navController: NavController,
    isCurrentUser: Boolean,
    userInfo: UserForProfile,
    fAndFViewModel: FAndFViewModel,
    selectedUserId: String,
    signInViewModel: SignInViewModel
) {

    val followersCount = fAndFViewModel.followersAndFollowing.value?.followers?.size

    val followingCount = fAndFViewModel.followersAndFollowing.value?.following?.size

    val userId = signInViewModel.userData.value?.uuid

    val targetId= selectedUserId

    Log.d("UserProfileScreen","$targetId")

    var isFollowing by remember { mutableStateOf(fAndFViewModel
        .followersAndFollowing.value?.followers?.any { it.uuid == userId } ==
            true) }

    Log.d("UserProfileScreen", "Tagrget: ${fAndFViewModel
        .followersAndFollowing.value?.followers}")

    val showFollowersSheet = remember { mutableStateOf(false) }

    val showFollowingSheet = remember { mutableStateOf(false) }

    val followersSheetState =  rememberModalBottomSheetState()

    val followingSheetState = rememberModalBottomSheetState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        ActionButtonColumn(
            buttonText = "Followers",
            count = followersCount,
            onClickAction = { showFollowersSheet.value = true },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.weight(0.2f))


        ActionButtonColumn(
            buttonText = "Following",
            count = followingCount,
            onClickAction = { showFollowingSheet.value = true },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.weight(0.2f))

        if(isCurrentUser) {
            ActionButtonColumn(
                buttonText = "Edit Profile", count = null, onClickAction = {
                    navController.navigate(NavigationRoutes.EDIT_PROFILE)
                },
                modifier = Modifier.weight(1.2f)
            )
        }

        Spacer(modifier = Modifier.weight(0.2f))

        if(!isCurrentUser){
            Log.d("UserProfileScreen","$isFollowing")
            val buttonText = when (isFollowing) {
                true -> "Unfollow"
                false -> "Follow"
            }

            ActionButtonColumn(buttonText = buttonText, count =null,
                onClickAction = {
                    userId?.let { uid ->
                        isFollowing = !isFollowing

                        if (isFollowing) {
                            fAndFViewModel.addFollow(uid, targetId)
                        } else {
                            fAndFViewModel.unfollow(uid, targetId)
                        }
                    }
                },
                modifier = Modifier.weight(1f))
        }

    }
    FollowersBottomSheet(showFollowersSheet, followersSheetState, fAndFViewModel)
    FollowingBottomSheet(showFollowingSheet, followingSheetState, fAndFViewModel)

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FollowersBottomSheet(
    showFollowersSheet: MutableState<Boolean>,
    sheetState: SheetState,
    FAndFViewModel: FAndFViewModel
) {
    if (showFollowersSheet.value) {
        val followers = FAndFViewModel.followersAndFollowing.value?.followers ?: listOf()

        ModalBottomSheet(
            onDismissRequest = { showFollowersSheet.value = false },
            sheetState = sheetState,
            modifier = Modifier.fillMaxHeight()
        ) {
            if (followers.isNotEmpty()) {
                followers.forEachIndexed { index, follower ->
                    FollowerCard(follower)
                    if (index < followers.size - 1) {
                        HorizontalDivider()
                    }
                }
            } else {
                EmptyContentBox("No Followers")
            }
        }
    }
}

@Composable
fun FollowerCard(follower: UserForProfile) {
    if (follower.name != null && follower.photo != null) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .clip(shape = RoundedCornerShape(10.dp))
                .clipToBounds()
        ) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .size(45.dp)
                    .clip(CircleShape)
                    .clipToBounds()
            ) {
                AppGlideSubcomposition(
                    imageUri = follower.photo
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = follower.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FollowingBottomSheet(
    showFollowingSheet: MutableState<Boolean>,
    sheetState: SheetState,
    FAndFViewModel: FAndFViewModel
) {
    if (showFollowingSheet.value) {
        val following = FAndFViewModel.followersAndFollowing.value?.following ?: listOf()

        ModalBottomSheet(
            onDismissRequest = { showFollowingSheet.value = false },
            sheetState = sheetState,
            modifier = Modifier.fillMaxHeight()
        ) {
            if (following.isNotEmpty()) {
                following.forEachIndexed { index, follow ->
                    FollowingCard(follow)
                    if (index < following.size - 1) {
                        HorizontalDivider()
                    }
                }
            } else {
                EmptyContentBox("No Following")
            }
        }
    }
}

@Composable
fun FollowingCard(follow: UserForProfile) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .padding(5.dp)
                .size(45.dp)
                .clip(CircleShape)
        ) {

            follow.photo?.let {
                AppGlideSubcomposition(
                    imageUri = it
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically)
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            if (follow.name != null) {
                Text(
                    text = follow.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}


@Composable
fun EmptyContentBox(text: String) {
    Box(
        modifier = Modifier.padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(text = text)
    }
}

@Composable
fun ActionButtonColumn(
    buttonText: String,
    count: Int?,
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


        Spacer(modifier = Modifier.height(2.dp))
        if (count !=null) {
            Text(
                text = "$count",
                style = MaterialTheme.typography.titleSmall
            )
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProfileContentPreview(){
//    ProfileContent(
//        userInfo = UserForProfile(
//            uuid = "123",
//            bio = "This bio is short and to the point, but it also conveys the " +
//                    "blogger's passion for food and cooking.",
//            name = "Sanjay",
//            photo = "https://lh3.googleusercontent.com/a/ACg8ocL6XmFlx179WwRBsXyuG7N3-8sMmyKUwVor3fgDuhCfUQ=s96-c"
//        ), navController = rememberNavController(), isCurrentUser = isCurrentUser
//    )
//}