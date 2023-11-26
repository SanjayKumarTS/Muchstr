package com.example.munchstr.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import com.cloudinary.transformation.Expression.height
import com.example.munchstr.model.CommentDto
import com.example.munchstr.utils.formatCreationTime

@Composable
fun CommentCard(comment: CommentDto?) {
    if (comment != null && comment?.userInfo?.name != null && comment?.userInfo?.photo != null) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .clip(shape = RoundedCornerShape(10.dp))
                .clipToBounds()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .width(45.dp)
                    .height
                        (45.dp)
                    .clip(CircleShape)
                    .clipToBounds()
            ) {
                AppGlideSubcomposition(
                    imageUri = comment.userInfo.photo
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .padding(5.dp)
                    .weight(1f)
                    .height(IntrinsicSize.Max),
                verticalArrangement =
                Arrangement
                    .SpaceEvenly
            ) {
                Text(
                    text = comment.userInfo.name,
                    style = MaterialTheme
                        .typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = comment.comment,
                    style = MaterialTheme
                        .typography
                        .bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
        }
    }
}