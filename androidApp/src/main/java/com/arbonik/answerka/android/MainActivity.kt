package com.arbonik.answerka.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arbonik.answerka.android.screens.AskScreen
import com.arbonik.answerka.android.screens.StartGameScreen
import com.arbonik.answerka.android.screens.TaskScreen
import com.arbonik.answerka.entity.GameState
import com.arbonik.answerka.viewmodels.GameViewModel
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val gameViewModel: GameViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val error = gameViewModel.errorMessage.collectAsState(null)

            if (error.value != null) {
                AlertDialog(error.value!!) {
                    gameViewModel.clearErrorMessage()
                }
            }
            val navController = rememberNavController()
            navController.enableOnBackPressed(false)

            NavHost(
                navController = navController,
                startDestination = AnswerkaNavigation.CreateGame.destinationPath
            ) {
                composable(AnswerkaNavigation.CreateGame.destinationPath) {
                    StartGameScreen(
                        gameViewModel = gameViewModel
                    )
                }
                composable(AnswerkaNavigation.Ask.destinationPath) {
                    AskScreen(
                        gameViewModel = gameViewModel,
                    )
                }
                composable(AnswerkaNavigation.Task.destinationPath) {
                    TaskScreen(
                        gameViewModel = gameViewModel,
                    )
                }
            }

            val gameState: GameState by gameViewModel.gameState.collectAsState()
            when (gameState) {
                is GameState.Ask -> {
                    navController.navigate(AnswerkaNavigation.Ask.destinationPath) {
                        launchSingleTop = true
                    }
                }
                GameState.INIT -> {
                    navController.navigate(AnswerkaNavigation.CreateGame.destinationPath){
                        launchSingleTop = true
                    }
                }
                is GameState.Task -> {
                    navController.navigate(AnswerkaNavigation.Task.destinationPath) {
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}

@Composable
fun AlertDialog(
    message: String,
    onDialogClicked: (Boolean) -> Unit
) {
    AlertDialog(onDismissRequest = {
        onDialogClicked(false)
    },
        title = { Text(text = stringResource(R.string.alert_title)) },
        text = { Text(text = message) },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorPrimary)),
                onClick = {
                    onDialogClicked(true)
                },
                content = {
                    Text(
                        text = "Ладно",
                        color = Color.White
                    )
                }
            )
        }
    )
}