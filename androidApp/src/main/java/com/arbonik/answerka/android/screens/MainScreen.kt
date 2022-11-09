package com.arbonik.answerka.android.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arbonik.answerka.android.R

@Preview
@Composable
fun MainScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .background(Color(0xFF212121))
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .weight(4f)
                .align(alignment = Alignment.CenterHorizontally)


        ) {
            Image(
                alignment = Alignment.CenterStart,
                painter = painterResource(id = R.drawable.title),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(0.8f)
        ) {
            MainButton(startIcon = R.drawable.ic_play, text = "Играть")
        }
    }
}

@Composable
fun MainButton(startIcon: Int, text: String) {
    OutlinedButton(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color(R.color.back),
            contentColor = Color.White,
        ),
        border = BorderStroke(3.dp, color = colorResource(id = R.color.teal_200)),
        shape = RoundedCornerShape(60),
    ) {
        Icon(
            painter = painterResource(startIcon),
            contentDescription = "Информация о приложении",
            modifier = Modifier.size(ButtonDefaults.IconSize + 20.dp)
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = text, fontSize = 20.sp)
    }
}