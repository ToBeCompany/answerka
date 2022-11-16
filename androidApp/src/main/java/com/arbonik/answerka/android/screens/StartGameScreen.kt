package com.arbonik.answerka.android.screens

import android.view.Surface
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arbonik.answerka.android.R
import com.arbonik.answerka.android.common.GreenStrokeButton
import com.arbonik.answerka.android.navigation.AnswerkaNavigation
import com.arbonik.answerka.database.TestGameRepository
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

    val isAddPlayerAlertShow = remember {
        mutableStateOf(false)
    }

    if (isAddPlayerAlertShow.value)
        TextRequestAlertDialog(
            message = stringResource(R.string.add_player),
            onDialogClicked = { name ->
                if (name != null)
                    gameViewModel.addPlayer(Player(name))
                isAddPlayerAlertShow.value = false
            }
        )

    Column(modifier = Modifier.background(colorResource(id = R.color.back))) {
        LazyColumn(
            Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(players) { player ->
                AddPlayerChip(
                    player = player,
                    onButtonClick = {
                        gameViewModel.removePlayer(it)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            GreenStrokeButton(
                modifier = Modifier.weight(3f),
                text = stringResource(R.string.start_game),
                onClick = {
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
                })
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    isAddPlayerAlertShow.value = true
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add player",
                    tint = colorResource(id = R.color.red)
                )
            }
        }
    }
}

@Composable
fun AddPlayerChip(
    player: Player,
    onButtonClick: (Player) -> Unit
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .background(color = colorResource(id = R.color.green))
                .width(2.dp)
                .fillMaxHeight()
        )
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = player.name,
            color = colorResource(id = R.color.white)
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = {
                onButtonClick(player)
            }) {
            Icon(
                modifier = Modifier.border(
                    BorderStroke(2.dp, colorResource(id = R.color.red)),
                    shape = CircleShape
                ),
                tint = colorResource(id = R.color.red),
                imageVector = Icons.Default.Close,
                contentDescription = "delete player"
            )
        }
    }
}

@Preview
@Composable
private fun StartCreenGamePreview() {
    val playerOne = Player("игроман")
    val playerTwo = Player("травоман")
//    val gameViewModel = GameViewModel(
//        TestGameRepository()
//    )
//    gameViewModel.addPlayer(playerOne)
//    gameViewModel.addPlayer(playerTwo)

//    StartGameScreen(gameViewModel = gameViewModel, navController = rememberNavController())
}


//TODO можно переиспользовать при добавлении собственных вопросов и заданий
@Composable
fun TextRequestAlertDialog(
    message: String,
    onDialogClicked: (String?) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }

    AlertDialog(onDismissRequest = {
        onDialogClicked(null)
    },
        title = {
            Text(text = message)
        },
        text = {
            val focusManager = LocalFocusManager.current
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                value = text,
                onValueChange = { text = it },
                label = { Text(text = stringResource(R.string.player_name)) },
                isError = text.isEmpty(),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(R.color.colorPrimary),
                    unfocusedBorderColor = Color(R.color.colorPrimary),
                    textColor = Color(R.color.back),
                    cursorColor = Color(R.color.green),
                    errorBorderColor = Color(R.color.colorPrimary),
                    focusedLabelColor = Color(R.color.back),
                    errorLabelColor = Color(R.color.back),
                    ),
                modifier = Modifier
                    .focusable(true)
                    .focusTarget()
                )
        },
        confirmButton = {
            Button(
                modifier = Modifier.width(width = 420.dp),
                border = BorderStroke(3.dp, color = colorResource(id = R.color.colorPrimary)),
                shape = RoundedCornerShape(60),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.colorPrimary)
                ),
                onClick = {
                    onDialogClicked(text)
                },
                content = {
                    Text(
                        text = "Добавить",
                        color = Color.White
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun TextRequestAlertDialogPreview() {
    TextRequestAlertDialog("как жизнь?") {

    }
}