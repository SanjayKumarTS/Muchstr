package com.example.munchstr.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.munchstr.R

@Composable
fun UserIconAndName(){
    Row (modifier = Modifier.height(IntrinsicSize.Max).padding(10.dp)){
        Image(painter =
        painterResource(id = R.drawable
            .dall_e_2023_10_19_10_14_13___photo_profile_pic_of_a_young_man_with_tan_skin__spiky_black_hair__and_hazel_eyes__he_has_a_playful_smirk_and_is_wearing_a_gray_t_shirt,),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier= Modifier
                .width(45.dp)
                .height
                    (45.dp)
                .clip(CircleShape)
                .clipToBounds()
        )
        Column(modifier = Modifier.fillMaxHeight().padding(start = 10.dp),
            verticalArrangement =
        Arrangement
            .SpaceEvenly) {
            Text(text = "FirstName LastName", style = MaterialTheme
                .typography.titleSmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
            Text(text = "2h ago", style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun UserIconAndNamePreview(){
    UserIconAndName()
}