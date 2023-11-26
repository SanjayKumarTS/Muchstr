package com.example.munchstr.ui.screens.addRecipe

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.munchstr.R
import com.example.munchstr.model.User
import com.example.munchstr.viewModel.RecipeViewModel
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import androidx.navigation.NavHostController
import com.example.munchstr.model.Ingredient
import com.example.munchstr.model.PostRecipe
import com.example.munchstr.model.Recipe
import com.example.munchstr.ui.components.AppGlideSubcomposition
import com.example.munchstr.viewModel.SignInViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipe(recipeViewModel: RecipeViewModel, navController:
NavHostController, signInViewModel: SignInViewModel){
    var recipeNameValue by remember { mutableStateOf("") }
    var recipePrepTime by remember { mutableStateOf("") }
    var recipeCookTime by remember { mutableStateOf("") }

    var recipeDescriptionValue by remember { mutableStateOf("") }
    var ingredients = remember { mutableStateListOf<com.example.munchstr.model.Ingredient>() }
    val imageUrl = recipeViewModel.imageUrl.value
    val navigateToHome by recipeViewModel.navigateToHome.collectAsState()
    val user by signInViewModel.userData.collectAsState()
    val context = LocalContext.current
    val categories = listOf("Breakfast", "Lunch", "Curry", "Snacks", "Desserts")
    val selectedCategories = remember { mutableStateListOf<String>() }

    var currentInstruction by remember { mutableStateOf("") }
    var instructions = remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(navigateToHome) {
        if (navigateToHome) {
            navController.navigate("home") {
            }
            recipeViewModel.navigateToHome.value = false
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            System.out.println(data)
            System.out.println(result.data)
            val bitmap = data?.extras?.get("data") as? Bitmap
            if (bitmap != null) {
                val imageUri = saveImage(bitmap, context)
                if (imageUri != null) {
                    uploadImageToCloudinary(imageUri,recipeViewModel)
                }
            } else {
                val imageUri: Uri? = data?.data
                imageUri?.let {
                    uploadImageToCloudinary(it,recipeViewModel)
                }
            }
        }
    }
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

                    Button(onClick = {
                        if (currentInstruction.isNotBlank()) {
                            instructions.value = instructions.value + currentInstruction
                            currentInstruction = ""
                        }
                        if (validateInputs(recipeNameValue, recipePrepTime,
                                recipeCookTime, recipeDescriptionValue, ingredients)) {
                            val newRecipe = user?.let {
                                PostRecipe(
                                    uuid = UUID.randomUUID().toString(),
                                    authorId = it.uuid,
                                    name = recipeNameValue,
                                    description = recipeDescriptionValue,
                                    ingredients = ingredients.toList(),
                                    instructions = instructions.value,
                                    nutrition = listOf(),
                                    preparationTime = recipePrepTime.toInt(),
                                    cookTime = recipeCookTime.toInt(),
                                    photo = imageUrl ?: "",
                                    tags = selectedCategories
                                )
                            }
                            if (newRecipe != null) {
                                recipeViewModel.postRecipe(newRecipe)
                            }
                        } else {
                            Toast.makeText(context, "Invalid input. Please check the recipe details.", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text("Post", color = MaterialTheme.colorScheme.onPrimary)
                    }

                },
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp
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
                    .clickable(onClick = {
                        showImageSourceDialog(
                            context,
                            imagePickerLauncher
                        )
                    })
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerHigh
                    )
            ) {
                if (imageUrl != null) {
                    AppGlideSubcomposition(imageUri = imageUrl, modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight())
                }
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
                        maxLines = 8,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text("Enter Recipe Steps", style = MaterialTheme.typography.titleMedium)

                instructions.value.forEachIndexed { index, instruction ->
                    OutlinedTextField(
                        value = instruction,
                        onValueChange = { updatedText ->
                            val updatedInstructions = instructions.value.toMutableList()
                            updatedInstructions[index] = updatedText
                            instructions.value = updatedInstructions
                        },
                        label = { Text("Step ${index + 1}") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                OutlinedTextField(
                    value = currentInstruction,
                    onValueChange = { currentInstruction = it },
                    label = { Text("Step ${instructions.value.size + 1}") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(
                        onClick = {
                            if (currentInstruction.isNotBlank()) {
                                instructions.value = instructions.value + currentInstruction
                                currentInstruction = ""
                            }
                        }
                    ) {
                        Text("Add Step")
                    }

                }
            }

            Row(modifier = Modifier
                .padding(
                    vertical = 10.dp, horizontal = 20.dp
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
                        value = recipePrepTime,
                        onValueChange = { newValue ->
                            if (newValue.all { char -> char.isDigit() }) {
                                recipePrepTime = newValue
                            }
                        },
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
                        "Cook Time",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Cook Time", style =
                        MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding()
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    OutlinedTextField(
                        value = recipeCookTime,
                        onValueChange = { newValue ->
                            if (newValue.all { char -> char.isDigit() }) {
                                recipeCookTime = newValue
                            }
                        },
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


            Column(modifier = Modifier.padding(16.dp)) {
                Text("Select Categories", style = MaterialTheme.typography.titleMedium)
                categories.forEach { category ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = selectedCategories.contains(category),
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedCategories.add(category)
                                } else {
                                    selectedCategories.remove(category)
                                }
                            }
                        )
                        Text(category)
                    }
                }
            }



        }
    }
}

@Composable
fun IngredientsList(ingredients: MutableList<com.example.munchstr.model.Ingredient>) {
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
                ingredients.add(com.example.munchstr.model.Ingredient(
                    name = "",
                    quantity = "",
                    unit = "",
                    note = ""
                ))
                println(ingredients)
            }, modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Ingredient")
        }
    }
}

@Composable
fun IngredientRow(ingredient: com.example.munchstr.model.Ingredient,onValueChange: (String, String, String) ->
Unit, onRemove: () -> Unit) {
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



data class Ingredient(
    var name: String = "",
    var quantity: String = "",
    var unit: String = ""
)



fun showImageSourceDialog(context: Context, launcher: ActivityResultLauncher<Intent>) {
    val options = arrayOf("Take Photo", "Choose from Gallery", "Cancel")
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Choose your profile picture")

    builder.setItems(options) { dialog, which ->
        when (options[which]) {
            "Take Photo" -> {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                launcher.launch(takePictureIntent)
            }
            "Choose from Gallery" -> {
                val pickPhotoIntent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                launcher.launch(pickPhotoIntent)
            }
            "Cancel" -> dialog.dismiss()
        }
    }
    builder.show()
}


fun uploadImageToCloudinary(uri: Uri, recipeViewModel:RecipeViewModel) {
    System.out.println(uri)

    MediaManager.get().upload(uri) .option("secure", true).callback(object : UploadCallback {
        override fun onStart(requestId: String) {

        }

        override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {

        }

        override fun onSuccess(requestId: String, resultData: Map<*, *>) {
            var uploadedImageUrl = resultData["url"] as String
            uploadedImageUrl = uploadedImageUrl.replaceFirst("http://", "https://")
            recipeViewModel.setImageUrl(uploadedImageUrl)
            println("image URL: $uploadedImageUrl")

        }

        override fun onError(requestId: String, error: ErrorInfo) {
            System.out.println("error ${error.description}");
        }

        override fun onReschedule(requestId: String, error: ErrorInfo) {
            System.out.println( "Reschedule ${error.description}");
        }
    }).dispatch()
}
fun saveImage(bitmap: Bitmap, context: Context): Uri? {
    val imagesFolder = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    var fileOutputStream: FileOutputStream? = null
    var file: File? = null
    try {
        file = File.createTempFile("captured_image", ".jpg", imagesFolder)
        fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            fileOutputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    return Uri.fromFile(file)
}
fun validateInputs(
    recipeName: String,
    prepTime: String,
    cookTime: String,
    description: String,
    ingredients: List<com.example.munchstr.model.Ingredient>
): Boolean {
    if (recipeName.isBlank()) {
        return false
    }

    val prepTimeInt = prepTime.toIntOrNull()
    val cookTimeInt = cookTime.toIntOrNull()
    if (prepTimeInt == null || cookTimeInt == null || prepTimeInt <= 0 || cookTimeInt <= 0) {
        return false
    }

    if (description.isBlank()) {
        return false
    }

    return true
}


