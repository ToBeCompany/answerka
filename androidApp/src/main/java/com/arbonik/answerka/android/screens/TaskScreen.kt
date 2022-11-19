package com.arbonik.answerka.android.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arbonik.answerka.android.R
import com.arbonik.answerka.android.navigation.AnswerkaNavigation
import com.arbonik.answerka.entity.Player
import com.arbonik.answerka.entity.SelectablePlayer
import com.arbonik.answerka.entity.Task
import com.arbonik.answerka.viewmodels.GameViewModel

@Composable
fun TaskScreen(
    gameViewModel: GameViewModel,
    navController: NavHostController,
) {
    val players : List<SelectablePlayer> by gameViewModel
        .selectedPlayers.collectAsState(listOf())
    val task : Task? by gameViewModel.currentTask.collectAsState()

    Column( modifier = Modifier
        .background(Color(0xFF212121)) // TODO использовать цвет из темы или ресурсов
        .fillMaxWidth()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(height = 72.dp, width = 128.dp)
                .align(alignment = CenterHorizontally)
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
        ){
            Text(
                text = "Игроки",
                fontSize = 26.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        LazyRow {
            items(players) { player ->
                if (player.isSelected)
                    PlayerChip(
                        player = player.player
                    )
            }
        }
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Divider(color = colorResource(id = R.color.red), thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Box(
                contentAlignment = Alignment.Center,
            ) {
                task?.let {
                    TaskView(task = it)
                }
            }
            Divider(color = colorResource(id = R.color.red), thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 8.dp))
        }
        Spacer(modifier = Modifier.weight(0.8f))
        MainButton(text = "Готово", R.color.red) {
            gameViewModel.nextStep()
            navController.navigate(AnswerkaNavigation.Ask.destinationPath) {
                launchSingleTop = true
                popUpTo(AnswerkaNavigation.CreateGame.destinationPath)
            }
        }
    }
}

@Composable
fun PlayerChip(
    player: Player
) {
    Card(
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 24.dp),
        shape = RoundedCornerShape(60),
        backgroundColor = colorResource(id = R.color.chip_grey),
        content = {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Icon(
                    modifier = Modifier
                        .border(
                            BorderStroke(2.dp, colorResource(id = R.color.red)),
                            shape = CircleShape,
                        )
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    tint = colorResource(id = R.color.red),
                    painter = painterResource(R.drawable.check),
                    contentDescription = "delete player"
                )
                Text(
                    text = player.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 0.dp, end = 8.dp)
                )
            }
        }
    )
}
@Composable
fun TaskView(
    task: Task
) {
    Text(
        text = task.text,
        color = Color.White,
        fontSize = 26.sp,
        modifier = Modifier
            .padding(top = 24.dp, bottom = 24.dp, start = 8.dp, end = 16.dp)
    )
}