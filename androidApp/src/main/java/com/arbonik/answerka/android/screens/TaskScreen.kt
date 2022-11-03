package com.arbonik.answerka.android.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arbonik.answerka.entity.Player
import com.arbonik.answerka.entity.SelectablePlayer
import com.arbonik.answerka.entity.Task
import com.arbonik.answerka.viewmodels.GameViewModel

@Composable
fun TaskScreen(
    gameViewModel: GameViewModel,
) {
    val players : List<SelectablePlayer> by gameViewModel
        .selectedPlayers.collectAsState(listOf())
    val task : Task? by gameViewModel.currentTask.collectAsState()

    BackHandler {
        gameViewModel.nextStep()
    }

    Column {
        LazyRow {
            items(players) { player ->
                if (player.isSelected)
                    PlayerChip(
                        player = player.player
                    )
            }
        }
        Button(onClick = {
            gameViewModel.nextStep()
        }) {
            Text(text = "done")
        }
        task?.let {
            TaskView(task = it)
        }
    }
}

@Composable
fun PlayerChip(
    player: Player
) {
    Card(
        content = {
            Text(text = player.name)
        }
    )
}

@Composable
fun TaskView(
    task: Task
) {
    Text(
        text = task.text
    )
}