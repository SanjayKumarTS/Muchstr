package com.example.munchstr.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.munchstr.R
import androidx.compose.ui.unit.dp

@Composable
fun CategoryScreen() {


    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {

        Spacer(modifier = Modifier.height(16.dp))

        Text("Categories", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))


        Row(modifier = Modifier.fillMaxWidth()) {
            CategoryCard(
                categoryName = "Breakfast",
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Min),
                onClick={},
                skillet = R.drawable.bread, )
            Spacer(modifier = Modifier.width(8.dp))
            CategoryCard(
                categoryName = "Lunch",
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Min),
                onClick={},
                skillet = R.drawable.lunch
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CategoryCard(categoryName = "Curry", modifier = Modifier
                .weight(1f)
                .height(IntrinsicSize.Min),  onClick={},
                R.drawable.skillet)
            Spacer(modifier = Modifier.width(8.dp))
            CategoryCard(
                categoryName = "Snacks",
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Min),
                onClick={},
                skillet = R.drawable.frenchfries
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        CategoryCard(
            categoryName = "Desserts", modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            onClick={},
            skillet = R.drawable.cake
        )
    }
}

@Composable
fun CategoryCard(
    categoryName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    skillet: Int
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable { },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(categoryName)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = skillet),
                contentDescription = "Skillet",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


