package com.example.munchstr.ui.screens.editProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.munchstr.model.User
import com.example.munchstr.model.UserDTO
import com.example.munchstr.model.UserForProfile
import com.example.munchstr.ui.components.AnimatedPreloader
import com.example.munchstr.ui.components.AppGlideSubcomposition
import com.example.munchstr.ui.navigation.NavigationRoutes
import com.example.munchstr.viewModel.SignInViewModel

@Composable
fun EditProfile(
    navController: NavController,
    signInViewModel: SignInViewModel,
){
    val userInfo by signInViewModel.userData.collectAsState()
    val selectedUser by signInViewModel.selectedUserData.collectAsState()


    val signOutSuccess by signInViewModel.signOutSuccess.collectAsState()
    if (signOutSuccess) {
        LaunchedEffect(Unit) {
            navController.navigate(NavigationRoutes.LOGIN) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
            signInViewModel.resetSignOutSuccess()
        }
    }

    if (userInfo != null) {
        EditProfileContent(navController = navController, signInViewModel =
        signInViewModel, userInfo = userInfo!!,selectedUser
        )
    } else {
        AnimatedPreloader()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileContent(
    navController: NavController,
    signInViewModel: SignInViewModel, userInfo: User, selectedUser: UserForProfile?
) {

    var name by remember { mutableStateOf(selectedUser?.name?.let { TextFieldValue(it) }) }
    var isNameEditable by remember { mutableStateOf(false) }
    var bio by remember { mutableStateOf(selectedUser?.bio ?: "") }
    var isBioEditable by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(
                        text = "Edit Profile",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                },
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp
                    ),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier
                .height(300.dp)
                .background(
                    color =
                    MaterialTheme.colorScheme.surfaceContainer
                )
                .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box (modifier = Modifier
                    .width(225.dp)
                    .height
                        (225.dp)
                    .clip(CircleShape)
                    .clipToBounds()){
                    userInfo.photoURL?.let { it1 ->
                        AppGlideSubcomposition(imageUri = it1,
                            modifier = Modifier.matchParentSize())
                    }
                }
            }
            Box (modifier = Modifier.fillMaxWidth()){
                Row(
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column (modifier = Modifier
                        .weight(1f)
                        .padding(
                            end = 10
                                .dp
                        )) {

                        Text(
                            text = "Name",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )

                        if (isNameEditable) {
                            name?.let { it1 ->
                                TextField(
                                    value = it1,
                                    onValueChange = { name = it },
                                    singleLine = true
                                )
                            }
                        } else {

                            name?.let { it1 -> Text(text = it1.text) }
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Name",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                isNameEditable = !isNameEditable
                                if (!isNameEditable) {
                                    val updatedUserDto =
                                        userInfo.photoURL?.let { it1 ->
                                            selectedUser?.bio?.let { it2 ->
                                                name?.let { it3 ->
                                                    UserDTO(
                                                        name = it3.text,
                                                        photo = it1,
                                                        uuid = userInfo.uuid,
                                                        bio = it2
                                                    )
                                                }
                                            }
                                        }
                                    if (updatedUserDto != null) {
                                        signInViewModel.updateUserData(
                                            userInfo.uuid,
                                            updatedUserDto
                                        )
                                    }
                                }
                            }
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
            Box (modifier = Modifier.fillMaxWidth()){
                Row(
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp)) {
                        Text(
                            text = "Bio",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )

                        if (isBioEditable) {
                            TextField(
                                value = bio,
                                onValueChange = { bio = it },
                                singleLine = true
                            )
                        } else {
                            Text(text = bio)
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Edit Bio",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                isBioEditable = !isBioEditable
                                if (!isBioEditable) {
                                    val updatedUserDto =
                                        userInfo.photoURL?.let { it1 ->
                                            selectedUser?.name?.let { it2 ->
                                                UserDTO(
                                                    name = it2,
                                                    photo = it1,
                                                    uuid = userInfo.uuid,
                                                    bio = bio
                                                )
                                            }
                                        }
                                    if (updatedUserDto != null) {
                                        signInViewModel.updateUserData(
                                            userInfo.uuid,
                                            updatedUserDto
                                        )
                                    }
                                }
                            }
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            signInViewModel.signOut()
                        }
                    )
            ){
                Row(modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth(),
                    verticalAlignment =
                    Alignment
                        .CenterVertically,
                ) {
                    Text(
                        text = "LOGOUT",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ExitToApp,
                        contentDescription = "",
                        tint = Color.DarkGray,
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        }
    }
}

//@Composable
//@Preview
//fun UserProfilePreview(){
//    UserProfile()
//}