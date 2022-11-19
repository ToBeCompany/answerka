package com.arbonik.answerka.android.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arbonik.answerka.android.R
import com.arbonik.answerka.android.navigation.AnswerkaNavigation
import com.arbonik.answerka.entity.Ask
import com.arbonik.answerka.entity.GameState
import com.arbonik.answerka.entity.Player
import com.arbonik.answerka.entity.SelectablePlayer
import com.arbonik.answerka.viewmodels.GameViewModel

@Composable
fun AskScreen(
    gameViewModel: GameViewModel,
    navController: NavHostController,
) {
    val currentAsk: Ask? by gameViewModel.currentAsk.collectAsState()

    val players: List<SelectablePlayer> by gameViewModel.selectedPlayers.collectAsState(listOf())

    Column(
        modifier = Modifier
            .background(Color(0xFF212121)) // TODO использовать цвет из темы или ресурсов
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(height = 72.dp, width = 128.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        ) {
            Image(
                alignment = Alignment.CenterStart,
                painter = painterResource(id = R.drawable.title),
                contentDescription = "",
                contentScale = ContentScale.Fit,
            )
        }
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.select_players),
                fontSize = 26.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Divider(
                color = colorResource(id = R.color.green),
                thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            currentAsk?.let {
                AskView(ask = it)
            }
            Divider(
                color = colorResource(id = R.color.green),
                thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        LazyRow {
            items(players) { player ->
                SelectedPlayerChip(selectedPlayer = player, onButtonClick = {
                    gameViewModel.changeSelectedPlayer(it)
                })
            }
        }
        Spacer(modifier = Modifier.weight(0.8f))
        MainButton(text = stringResource(R.string.done), R.color.green) {
            when (gameViewModel.gameState.value) {
                is GameState.Task -> navController.navigate(AnswerkaNavigation.Task.destinationPath) {
                    launchSingleTop = true
                }

                is GameState.Ask -> {
                    gameViewModel.nextStep()
                    if (gameViewModel.gameState.value is GameState.Task) navController.navigate(
                        AnswerkaNavigation.Task.destinationPath
                    ) {
                        launchSingleTop = true
                    }
                }

                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedPlayerChip(
    selectedPlayer: SelectablePlayer, onButtonClick: (Player) -> Unit
) {
    FilterChip(
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 24.dp),
        shape = RoundedCornerShape(60),
        selected = selectedPlayer.isSelected,
        border = FilterChipDefaults.filterChipBorder(borderColor = colorResource(id = R.color.chip_grey), borderWidth = 2.dp),
        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = colorResource(id = R.color.chip_grey)),
        onClick = {
            onButtonClick(selectedPlayer.player)
        },
        label = {
            Text(
                selectedPlayer.player.name, fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
            )
        },
        leadingIcon = if (selectedPlayer.isSelected) {
            {
                Icon(
                    modifier = Modifier
                        .border(
                            BorderStroke(2.dp, colorResource(id = R.color.green)),
                            shape = CircleShape,
                        )
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    tint = colorResource(id = R.color.green),
                    painter = painterResource(R.drawable.check),
                    contentDescription = "delete player"
                )
            }
        } else {
            null
        }
    )
}

@Composable
fun AskView(
    ask: Ask
) {
    Text(
        text = ask.text,
        color = Color.White,
        fontSize = 26.sp,
        modifier = Modifier.padding(top = 24.dp, bottom = 24.dp, start = 8.dp, end = 16.dp)
    )
}