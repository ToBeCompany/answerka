package com.arbonik.answerka.android.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun PaymentScreen() {
    Column {
        Text("Категория 18+")
        Text("Получите доступ к самым горячим вопросом чтобы повысить градус игры ;)")
        Button(onClick = { }) {
            Text("Купить!")
        }
    }
}