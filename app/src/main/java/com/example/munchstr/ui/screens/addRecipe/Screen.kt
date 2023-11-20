package com.example.munchstr.ui.screens.addRecipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.munchstr.R
import com.example.munchstr.ui.components.PrepTimeAndCookTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipe(){
    var recipeNameValue by remember { mutableStateOf("") }
    var recipeDescriptionValue by remember { mutableStateOf("") }
    val ingredients = remember { mutableStateListOf(Ingredient()) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Text("What's Munchin?")
                        Image(painter = painterResource(id = R.drawable
                            .cookie), contentDescription = "",
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
                        },
            actions = {
                Button(onClick = { /* TODO: Handle post action */ }) {
                    Text("Post", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
                modifier = Modifier
                    .shadow(
                        elevation = 10
                            .dp
                    )
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerHigh
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable
                            .add_a_photo
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )
            }
            Box(modifier = Modifier.padding(vertical = 10.dp, horizontal = 20
                .dp)){
                Column {
                    Text(
                        text = "Recipe Name",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    OutlinedTextField(
                        value = recipeNameValue,
                        onValueChange = { recipeNameValue = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.surfaceContainerHigh),
                        maxLines = 1,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Box(modifier = Modifier.padding(vertical = 10.dp, horizontal = 20
                .dp)){
                Column {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    OutlinedTextField(
                        value = recipeDescriptionValue,
                        onValueChange = { recipeDescriptionValue = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.surfaceContainerHigh)
                            .height(150.dp),
                        maxLines = 10,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Row(modifier = Modifier
                .padding(
                    vertical = 10.dp, horizontal = 20
                        .dp
                )
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment
                            .CenterHorizontally, modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                id = R
                                    .drawable.schedule,
                            ),
                            contentDescription =
                            "Prep Time",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Prep Time", style =
                            MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        OutlinedTextField(
                            value = recipeDescriptionValue,
                            onValueChange = { recipeDescriptionValue = it },
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme
                                        .surfaceContainerHigh
                                )
                                .width(100.dp),
                            maxLines = 1,
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                id = R
                                    .drawable.skillet
                            ),
                            contentDescription =
                            "Prep Time",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Prep Time", style =
                            MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding()
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        OutlinedTextField(
                            value = recipeDescriptionValue,
                            onValueChange = { recipeDescriptionValue = it },
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme
                                        .surfaceContainerHigh
                                )
                                .width(100.dp),
                            maxLines = 1,
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                    }
            }
            Box(modifier = Modifier.padding(vertical = 10.dp, horizontal = 20
                .dp)){
                Column {
                    IngredientsList(ingredients = ingredients)
                }
            }
        }
    }
}

@Composable
fun IngredientsList(ingredients: MutableList<Ingredient>) {
    Column (modifier = Modifier.fillMaxWidth()){
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        ingredients.forEachIndexed { index, ingredient ->
            IngredientRow(
                ingredient = ingredient,
                onValueChange = { newName, newQuantity, newUnit ->
                    // Update the ingredient with the new values.
                    ingredients[index] = ingredient.copy(name = newName, quantity = newQuantity, unit = newUnit)
                },
                onRemove = {
                    if (ingredients.size > 1) {
                        ingredients.removeAt(index)
                    }
                }
            )
        }
        IconButton(
            onClick = {
            ingredients.add(Ingredient())
                println(ingredients)
        }, modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Ingredient")
        }
    }
}

@Composable
fun IngredientRow(ingredient: Ingredient,onValueChange: (String, String, String) -> Unit, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = ingredient.name,
            onValueChange = { newName ->
                onValueChange(newName, ingredient.quantity, ingredient.unit)
            },
            modifier = Modifier.weight(1f),
            label = { Text("Name") }
        )
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedTextField(
            value = ingredient.quantity,
            onValueChange = { newQuantity ->
                onValueChange(ingredient.name, newQuantity, ingredient.unit)
            },
            modifier = Modifier.weight(1f),
            label = { Text("Qty") }
        )
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedTextField(
            value = ingredient.unit,
            onValueChange = { newUnit ->
                onValueChange(ingredient.name, ingredient.quantity, newUnit)
            },
            modifier = Modifier.weight(1f),
            label = { Text("Unit") }
        )
        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Close, contentDescription = "Remove Ingredient")
        }
    }
}


@Composable
@Preview(showBackground = true)
fun AddRecipePreview(){
    AddRecipe()
}

data class Ingredient(
    var name: String = "",
    var quantity: String = "",
    var unit: String = ""
)
