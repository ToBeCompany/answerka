package com.arbonik.answerka.android.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arbonik.answerka.android.R

@Composable
fun GreenStrokeButton(
    text : String,
    onClick : () -> Unit,
    modifier: Modifier = Modifier,
    icon : ImageVector? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = onClick)
            .border(
                BorderStroke(2.dp, colorResource(id = R.color.green)),
                shape = CircleShape
            )
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        if (icon != null){
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
        }
        Text(
            text = text,
            color = Color.White
        )
    }
}

@Preview(backgroundColor = R.color.black.toLong())
@Composable
fun GreenStrokeButtonPreview() {
    GreenStrokeButton(
        "Нажми",
        onClick = {},
        icon = Icons.Default.Close
    )
}