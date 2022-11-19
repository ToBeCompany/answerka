package com.arbonik.answerka.android.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arbonik.answerka.android.R
import com.arbonik.answerka.android.common.GreenStrokeButton
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            GreenStrokeButton(
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 6.dp, end = 6.dp),
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
            FloatingActionButton(
                containerColor = colorResource(id = R.color.red),
                shape = RoundedCornerShape(100.dp),
                onClick = {
                    isAddPlayerAlertShow.value = true
                          },
                modifier = Modifier.padding(end = 16.dp),
            ) {
                Icon(Icons.Outlined.Person,
                    contentDescription = "add player",
                    tint = colorResource(id = R.color.white)
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
            .fillMaxWidth()
            .padding(start = 8.dp),
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
            fontSize = 18.sp,
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
        containerColor = colorResource(id = R.color.colorPrimaryDark),
        title = {
            Text(text = message, fontSize = 18.sp, color = colorResource(id = R.color.white))
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
                label = { Text(text = stringResource(R.string.player_name),color = colorResource(id = R.color.white)) },
                isError = text.isEmpty(),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(colorResource(id = R.color.white)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id =R.color.colorPrimary),
                    unfocusedBorderColor = colorResource(id =R.color.colorPrimary),
                    cursorColor = colorResource(id =R.color.green),
                    errorBorderColor = colorResource(id =R.color.red),
                    focusedLabelColor = colorResource(id =R.color.white),
                    errorLabelColor = colorResource(id =R.color.white),
                ),
                modifier = Modifier
                    .focusable(true)
                    .focusTarget()
            )
        },
        confirmButton = {
            Button(
                modifier = Modifier
                    .width(width = 420.dp)
                    .padding(vertical = 6.dp),
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
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 6.dp)
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