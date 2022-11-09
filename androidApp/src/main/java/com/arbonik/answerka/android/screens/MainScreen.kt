package com.arbonik.answerka.android.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.arbonik.answerka.android.R
import com.arbonik.answerka.android.navigation.AnswerkaNavigation

@Composable
fun MainScreen(
    navController: NavController
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .background(Color(0xFF212121)) // TODO использовать цвет из темы или ресурсов
        .fillMaxWidth()){
        Box(modifier = Modifier.weight(4f)){
            Image(painter = painterResource(id = R.drawable.title), contentDescription = "")
        }
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxWidth(0.8f)) {
            MainButton(startIcon = R.drawable.ic_play, text = "Играть"){
                navController.navigate(AnswerkaNavigation.CreateGame.destinationPath)

            }
        }
        Button(onClick = {
            navController.navigate(AnswerkaNavigation.Settings.destinationPath)
        }) {
            Text(text = "Настройки")
        }
        Button(onClick = {
            navController.navigate(AnswerkaNavigation.Payment.destinationPath)
        }) {
            Text(text = "Премиум")
        }
    }
}

@Composable
fun MainButton(startIcon: Int, text: String, onClick : () -> Unit= { }){
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Icon(
            painter = painterResource(startIcon),
            contentDescription = "Информация о приложении",
            tint = Color.Red,
            modifier = Modifier.weight(1f)
        )

//        Box(modifier = Modifier.weight(4f).align(Alignment.CenterHorizontally)) {
        Text(text = text)
//        }
    }
}