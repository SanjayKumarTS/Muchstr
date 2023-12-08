package com.example.munchstr.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.munchstr.viewModel.RecipeViewModel

@Composable
fun SearchBar(
    recipeViewModel: RecipeViewModel,
    searchString: String,
    onSearchChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    var text by remember { mutableStateOf(searchString) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .padding(top = 3.dp, bottom = 3.dp, start = 5.dp, end = 50.dp)
            .clickable { focusManager.clearFocus() } ,
        shape = RoundedCornerShape(40),
        color = Color(223,213,236)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .height(45.dp)
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                    onSearchChanged(it)
                },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearchClicked(text)
                    recipeViewModel.selectbutton(true)
                    focusManager.clearFocus()
                }),
                modifier = Modifier.padding(horizontal = 20.dp).weight(1f)
                    .focusRequester(focusRequester)
                    .clickable { focusRequester.requestFocus() }
            )
            IconButton(
                onClick = {
                    onSearchClicked(text)
                    recipeViewModel.selectbutton(true)
                    focusManager.clearFocus()
                },
                modifier = Modifier.padding(5.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        }
    }
}