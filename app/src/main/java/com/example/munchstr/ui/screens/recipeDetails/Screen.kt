package com.example.munchstr.ui.screens.recipeDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.munchstr.model.Ingredient
import com.example.munchstr.model.Nutrition
import com.example.munchstr.ui.components.AppCheckBox
import com.example.munchstr.ui.components.AppGlideSubcomposition
import com.example.munchstr.ui.components.AppNumberingList
import com.example.munchstr.ui.components.CommentButton
import com.example.munchstr.ui.components.CommentCard
import com.example.munchstr.ui.components.LikeButton
import com.example.munchstr.ui.components.PrepTimeAndCookTime
import com.example.munchstr.ui.components.SaveButton
import com.example.munchstr.ui.components.Sharebutton
import com.example.munchstr.ui.components.UserIconAndName
import com.example.munchstr.viewModel.CommentViewModel
import com.example.munchstr.viewModel.RecipeViewModel
import com.example.munchstr.viewModel.SignInViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun RecipeDetails(
    navController: NavHostController,
    recipeViewModel: RecipeViewModel,
    commentViewModel: CommentViewModel,
    recipeId: String,
    likesCount: Int,
    commentsCount: Int,
    isLiked: Boolean,
    signInViewModel: SignInViewModel
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    LaunchedEffect(Unit){
        recipeViewModel.loadSingleRecipe(recipeId)
    }
    val recipe = recipeViewModel.selectedRecipe.value
    val author = recipeViewModel.selectedRecipeAuthor.value
    val comments = commentViewModel.comments
    val userInfo = signInViewModel.userData

    val ptrState=
        rememberPullRefreshState(recipeViewModel.isRefreshing.value, {recipeViewModel.loadSingleRecipe(recipeId)})

    Column {
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
                            text = "Recipe Details",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                    modifier = Modifier
                        .shadow(
                            elevation = 10
                                .dp
                        ),
                    colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /*TODO*/ }, containerColor =
                    MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "",

                        )
                }
            },
        ) {
            Box(modifier = Modifier
                .padding(it)
                .pullRefresh(ptrState)
                .fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .verticalScroll(
                            rememberScrollState()
                        ),
                ) {
                    Box(
                        Modifier
                            .padding(all = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Column {
                            if (recipe != null) {
                                Text(
                                    text = recipe.tags.first(),
                                    style = MaterialTheme.typography
                                        .titleSmall,
                                    textDecoration = TextDecoration
                                        .Underline,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(bottom = 5.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier.weight(
                                        1f,
                                        fill = false
                                    )
                                ) {
                                    if (recipe != null) {
                                        Text(
                                            text = recipe.name,
                                            style = MaterialTheme
                                                .typography.titleLarge,
                                            color = MaterialTheme
                                                .colorScheme.onSecondaryContainer,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    if (author != null && recipe != null) {
                                        UserIconAndName(name = author.name,
                                            photo = author
                                            .photo, creationTime = recipe.createdAt)
                                    }
                                }
                                if (recipe != null) {
                                    PrepTimeAndCookTime(
                                        prepTime = recipe
                                            .preparationTime,
                                        cookTime = recipe.cookTime
                                    )
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(330.dp)
                    ) {
                        if (recipe != null) {
                            AppGlideSubcomposition(imageUri = recipe.photo)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(modifier = Modifier.padding(bottom = 5.dp)) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                LikeButton(
                                    tint = Color.Unspecified,
                                    liked = isLiked,
                                    onClick = {
                                        userInfo.value?.let { it1 ->
                                            recipeViewModel.addLike(recipeId,
                                                it1.uuid)
                                        }
                                    }
                                    )
                                Text(
                                    text = likesCount.toString(), style = MaterialTheme
                                        .typography.labelSmall
                                )
                            }
                            CommentButton(modifier = Modifier,
                            onClick = {
                                showBottomSheet = true
                                if(commentsCount!=0){
                                    commentViewModel.updateComments(recipeId = recipeId)
                                }
                            })
                            Sharebutton(content = "")
                        }
                        SaveButton {
                            if (recipe != null && author != null) {
                                recipeViewModel
                                    .insertRecipeToDatabase(recipe = recipe,
                                        author = author)
                            }
                        }
                    }
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                    Column(modifier = Modifier.padding(10.dp)) {
                        if (recipe != null) {
                            RecipeDescription(description = recipe.description)
                        }
                        if (recipe != null) {
                            RecipeIngredients(ingredients = recipe.ingredients)
                        }
                        if (recipe != null) {
                            RecipeInstructions(instructions = recipe.instructions)
                        }
                        if (recipe != null) {
                            RecipeNutritionalFacts(facts = recipe.nutrition)
                        }
                    }
                    if (showBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                showBottomSheet = false
                            },
                            sheetState = sheetState,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            if(comments.isNotEmpty()){
                                comments.forEachIndexed { index, comment ->
                                    CommentCard(comment)
                                    if (index < comments.size - 1) {
                                        HorizontalDivider()
                                    }
                                }
                            }
                            else{
                                Box(
                                    modifier = Modifier.padding(10.dp),
                                    contentAlignment = Alignment.TopCenter
                                    ) {
                                    Text(text = "No Comments")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeDescription(description: String) {
    Column (modifier = Modifier.padding(5.dp)){
        Text(
            text = "Description",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Text(text = description,
            style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun RecipeIngredients(ingredients: List<Ingredient>) {
    Column(modifier = Modifier.padding(5.dp)) {
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Column {
            ingredients.forEach { it ->
                AppCheckBox(text = "${it.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }} ${it.quantity} " +
                        it.unit
                )
            }
        }
    }
}

@Composable
fun RecipeInstructions(instructions: List<String>){
    Column(modifier = Modifier.padding(5.dp)) {
        Text(
            text = "Instructions",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Column {
            instructions.forEachIndexed { index, instruction ->
                AppNumberingList(listNumber = index + 1, text = instruction)
            }
        }
    }
}

@Composable
fun RecipeNutritionalFacts(facts: List<Nutrition>){
    Column(modifier = Modifier.padding(10.dp)) {
        Text(
            text = "Nutritional Facts",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Column {
            NutritionalFacts(facts)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NutritionalFacts(facts: List<Nutrition>) {
    FlowRow(
        horizontalArrangement = Arrangement.Center
    ) {
        facts.forEach {
            NutrientLabel(nutrientName = it.label, amount = "${it.value} ${it.unit}")
        }
    }
}

@Composable
fun NutrientLabel(nutrientName: String, amount: String) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .clip(shape = RoundedCornerShape(16.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(start = 10.dp, top = 4.dp, bottom = 4.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text(
                    text = nutrientName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text(
                    text = amount,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun RecipeDetailsPreview() {
//    RecipeDetails(navController, it.arguments?.getString("recipeId"))
//}
