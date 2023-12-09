package com.example.munchstr.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.munchstr.R
import com.example.munchstr.utils.formatTimeInMinutes

@Composable
fun PrepTimeAndCookTime(prepTime: Int, cookTime: Int) {
    Row {
        Column(
            horizontalAlignment = Alignment
                .CenterHorizontally, modifier = Modifier
                .padding(5.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = R
                        .drawable.schedule
                ),
                contentDescription =
                "Prep Time",
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Prep Time", style =
                MaterialTheme.typography.labelSmall
            )
            Text(
                text = formatTimeInMinutes(prepTime), style =
                MaterialTheme.typography.labelSmall
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
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Cook Time", style =
                MaterialTheme.typography.labelSmall
            )
            Text(
                text = formatTimeInMinutes(cookTime), style =
                MaterialTheme.typography.labelSmall
            )
        }
    }
}