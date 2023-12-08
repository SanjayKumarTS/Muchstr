package com.example.munchstr.ui.components

import android.content.Intent
import android.text.Html
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.munchstr.model.Ingredient
import com.example.munchstr.model.Recipe

@Composable
fun Sharebutton(recipe: Recipe){
    val context = LocalContext.current
    IconButton(onClick = {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                Html.fromHtml(
                    "<b>${recipe.name}</b><br>" +
                            "<br>${recipe.description}"+
                    "<br><br><b>Ingredients</b><br><br>"+
                            convertIngredientsToString(recipe.ingredients) +
                            "<br><br><b>Instructions</b><br><br>"+
                            convertInstructionsToString(recipe.instructions)
                            )
            )
            type = "text/html"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }) {
        Icon(imageVector = Icons.Filled.Share, contentDescription = "Share")
    }
}
fun convertIngredientsToString(ingredients: List<Ingredient>): String {
    return ingredients.mapIndexed { index, ingredient ->
        "${index + 1}. ${ingredient.name}: ${ingredient.quantity} ${ingredient.unit} ${ingredient.note}"
    }.joinToString("<br>")
}
fun convertInstructionsToString(instructions: List<String>): String {
    return instructions.mapIndexed { index, instruction ->
        "${index + 1}. $instruction"
    }.joinToString("<br>")
}

