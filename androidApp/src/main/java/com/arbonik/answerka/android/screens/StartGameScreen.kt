package com.arbonik.answerka.android.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.arbonik.answerka.android.R
import com.arbonik.answerka.android.navigation.AnswerkaNavigation
import com.arbonik.answerka.entity.GameState
import com.arbonik.answerka.entity.Player
import com.arbonik.answerka.viewmodels.GameViewModel


@Composable
fun StartGameScreen(
    gameViewModel: GameViewModel,
    navController: NavHostController
) {
    val players: List<Player> by gameViewModel.players
        .collectAsState()

    Column {
        LazyRow {
            items(players) { player ->
                AddPlayerChip(
                    player = player,
                    onButtonClick = {
                        gameViewModel.removePlayer(it)
                    }
                )
            }
        }

        var text by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text = stringResource(R.string.player_name)) },
            isError = text.isEmpty()
        )
        Button(onClick = {
            gameViewModel.addPlayer(Player(text))
            text = ""
        }) {
            Text(text = stringResource(R.string.add_player))
        }
        Button(onClick = {
            when (gameViewModel.gameState.value) {
                is GameState.GamePause -> {
                    gameViewModel.nextStep()
                    if (gameViewModel.gameState.value is GameState.Ask)
                        navController.navigate(AnswerkaNavigation.Ask.destinationPath) {
                            launchSingleTop = true
                        }
                }
                is GameState.Ask -> navController.navigate(AnswerkaNavigation.Ask.destinationPath) {
                    launchSingleTop = true
                }
                is GameState.Task -> navController.navigate(AnswerkaNavigation.Task.destinationPath) {
                    launchSingleTop = true
                }
            }
        }) {
            Text(text = stringResource(R.string.start_game))
        }
    }
}

@Composable
fun AddPlayerChip(
    player: Player,
    onButtonClick: (Player) -> Unit
) {
    Button(onClick = {
        onButtonClick(player)
    }) {
        Text(text = player.name)
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "delete player"
        )
    }
}