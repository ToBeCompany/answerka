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
    Column {
        Button(onClick = {
            navController.navigate(AnswerkaNavigation.CreateGame.destinationPath)
        }) {
            Text(text = "Играть")
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