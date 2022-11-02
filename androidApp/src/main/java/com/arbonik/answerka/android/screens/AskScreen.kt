package com.arbonik.answerka.android.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.arbonik.answerka.android.R
import com.arbonik.answerka.entity.Ask
import com.arbonik.answerka.entity.Player
import com.arbonik.answerka.entity.SelectablePlayer
import com.arbonik.answerka.viewmodels.GameViewModel

@Composable
fun AskScreen(
    gameViewModel: GameViewModel,
) {

    val currentTask : Ask? by gameViewModel.currentAsk
        .collectAsState()

    val players: List<SelectablePlayer> by gameViewModel.selectedPlayers
        .collectAsState(listOf())

    Column {
        Text(
            text = stringResource(R.string.select_players)
        )
        currentTask?.let {
            AskView(ask = it)
        }
        LazyRow {
            items(players) { player ->
                SelectedPlayerChip(
                    selectedPlayer = player,
                    onButtonClick = {
                        gameViewModel.changeSelectedPlayer(it)
                    }
                )
            }
        }
        Button(onClick = {
            gameViewModel.nextStep()
        }) {
            Text(text = stringResource(R.string.done))
        }
    }
}

@Composable
fun SelectedPlayerChip(
    selectedPlayer: SelectablePlayer,
    onButtonClick: (Player) -> Unit
) {
    Button(
        onClick = {
        onButtonClick(selectedPlayer.player)
    }) {
        Text(text = selectedPlayer.player.name)
        if (selectedPlayer.isSelected)
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "selected player"
            )
    }
}


@Composable
fun AskView(
    ask: Ask
) {
    Text(
        text = ask.text
    )
}