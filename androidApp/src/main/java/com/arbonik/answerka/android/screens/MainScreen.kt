package com.arbonik.answerka.android.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arbonik.answerka.android.R
import com.arbonik.answerka.android.navigation.AnswerkaNavigation

@Composable
fun MainButtonWithIcon(startIcon: Int, text: String, function: () -> Unit) {
    OutlinedButton(
        onClick = function,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(68.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color(0xFF212121),
            contentColor = Color.White,
        ),
        border = BorderStroke(3.dp, color = colorResource(id = R.color.teal_200)),
        shape = RoundedCornerShape(60),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Icon(
                painter = painterResource(startIcon),
                contentDescription = "Информация о приложении",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.size(14.dp))
            Text(text = text, fontSize = 20.sp)
        }
    }
}

@Composable
fun MainButton(text: String, color: Int, function: () -> Unit) {
    OutlinedButton(
        onClick = function,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(68.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color(0xFF212121),
            contentColor = Color.White,
        ),
        border = BorderStroke(3.dp, color = colorResource(color)),
        shape = RoundedCornerShape(60),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Text(text = text, fontSize = 20.sp)
        }
    }
}

@Composable
fun MainScreen(
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .background(Color(0xFF212121)) // TODO использовать цвет из темы или ресурсов
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .weight(1.5f)
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
                .fillMaxWidth(0.8f)){
            Column {
                MainButtonWithIcon(startIcon = R.drawable.ic_play, text = "Играть") {
                    navController.navigate(AnswerkaNavigation.CreateGame.destinationPath)
                }
                MainButtonWithIcon(startIcon = R.drawable.settings, text = "Настройки") {
                    navController.navigate(AnswerkaNavigation.Settings.destinationPath)
                }
                MainButtonWithIcon(startIcon = R.drawable.premium, text = "Премиум") {
                    navController.navigate(AnswerkaNavigation.Payment.destinationPath)
                }
            }
        }
    }
}