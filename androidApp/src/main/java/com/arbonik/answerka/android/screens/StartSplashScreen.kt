package com.arbonik.answerka.android.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.arbonik.answerka.android.R
import com.arbonik.answerka.android.navigation.AnswerkaNavigation
import kotlinx.coroutines.delay

@Preview
@Composable
fun StartSplashScreen(
    navController : NavController
){
    LaunchedEffect(true){
        delay(1000)
        navController.navigate(AnswerkaNavigation.Main.destinationPath) {
            navController.popBackStack(
                route = AnswerkaNavigation.Splash.destinationPath,
                true
            )
        }
    }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painterResource(R.drawable.splash), contentDescription = "", contentScale = ContentScale.Crop)
    }
}