package com.example.munchstr.ui.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun AppNumberingList(listNumber:Number,text:String){
    var checkedState by remember { mutableStateOf(false) }
    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.Top
    ) {
        ClickableText(
            text = AnnotatedString("$listNumber."),
            style = TextStyle(
                textDecoration = if(checkedState) TextDecoration.LineThrough else
                    TextDecoration.None,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
            ),
            onClick = {
                checkedState = !checkedState
            }
        )
        ClickableText(
            text = AnnotatedString(text),
            style = TextStyle(
                textDecoration = if(checkedState) TextDecoration.LineThrough else
                    TextDecoration.None,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
            ),
            modifier = Modifier.padding(start = 10.dp),
            onClick = {
                checkedState = !checkedState
            }
        )
    }
}