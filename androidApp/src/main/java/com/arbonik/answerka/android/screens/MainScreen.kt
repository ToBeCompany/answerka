package com.arbonik.answerka.android.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.arbonik.answerka.android.navigation.AnswerkaNavigation

@Composable
fun MainScreen(
    navController: NavController
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .background(Color(0xFF212121))
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