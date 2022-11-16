package com.arbonik.answerka.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arbonik.answerka.android.screens.*
import androidx.navigation.navigation
import com.arbonik.answerka.android.navigation.AnswerkaGraph
import com.arbonik.answerka.android.navigation.AnswerkaNavigation
import com.arbonik.answerka.viewmodels.GameViewModel
import com.arbonik.answerka.viewmodels.PaymentViewModel
import com.arbonik.answerka.viewmodels.SettingViewModel
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.concurrent.timerTask


class MainActivity : AppCompatActivity() {

    private val gameViewModel: GameViewModel by inject()
    private val paymentViewModel: PaymentViewModel by inject()
    private val settingViewModel: SettingViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //TODO установить тему, чтобы не назначать фон на экранах и другое
            SetupNavGraph()
        }
    }

    @Composable
    private fun SetupNavGraph() {
        val error = gameViewModel.errorMessage.collectAsState(null)

        if (error.value != null) {
            AlertDialog(error.value!!) {
                gameViewModel.clearErrorMessage()
            }
        }

        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = AnswerkaNavigation.Splash.destinationPath
        ) {
            composable(AnswerkaNavigation.Splash.destinationPath) {
                Timer().schedule(timerTask {
                    runOnUiThread {
                        navController.navigate(AnswerkaNavigation.Main.destinationPath) {
                            navController.popBackStack(
                                route = AnswerkaNavigation.Splash.destinationPath,
                                true
                            )
                        }
                    }
                }, 1000)
                StartSplashScreen()
            }
            composable(AnswerkaNavigation.Main.destinationPath) {
                MaterialTheme {
                    MainScreen(navController)
                }
            }
            composable(AnswerkaNavigation.Settings.destinationPath) {
                SettingsScreen(
                    settingViewModel,
                    gameViewModel
                )
            }
            composable(AnswerkaNavigation.Payment.destinationPath) {
                PaymentScreen(paymentViewModel)
            }

            navigation(
                route = AnswerkaGraph.GAME,
                startDestination = AnswerkaNavigation.CreateGame.destinationPath
            ) {
                composable(AnswerkaNavigation.CreateGame.destinationPath) {
                    StartGameScreen(
                        gameViewModel = gameViewModel,
                        navController
                    )
                }
                composable(AnswerkaNavigation.Ask.destinationPath) {
                    AskScreen(
                        gameViewModel = gameViewModel,
                        navController
                    )
                }
                composable(AnswerkaNavigation.Task.destinationPath) {
                    TaskScreen(
                        gameViewModel = gameViewModel,
                        navController
                    )
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
        title = {
            Text(text = stringResource(R.string.alert_title))
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.colorPrimary)
                ),
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