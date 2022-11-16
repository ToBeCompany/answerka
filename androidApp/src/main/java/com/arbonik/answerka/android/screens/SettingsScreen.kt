package com.arbonik.answerka.android.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arbonik.answerka.android.R
import com.arbonik.answerka.viewmodels.GameViewModel
import com.arbonik.answerka.viewmodels.SettingViewModel

@Composable
fun SettingsScreen(
    settingsViewModel: SettingViewModel,
    gameViewModel: GameViewModel
) {

    LaunchedEffect(true) {
        settingsViewModel.loadSettings()
    }
    val categories = settingsViewModel
        .categories
        .collectAsState()
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.back))
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Наборы данных для игры:",
            color = Color.White
        )
        LazyColumn(content = {
            items(categories.value) { category ->
                if (category != null)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = category.name,
                            color = Color.White
                        )
                        Checkbox(checked = category.isEnabled, onCheckedChange = { checked ->
                            settingsViewModel.updateCategory(category.copy(isEnabled = checked))
                            gameViewModel.reloadData()
                        })
                    }
            }
        })
        Text(
            text = "После изменения настроек данные загрузятся заново",
            color = Color.White
        )
    }
}

@Preview
@Composable
fun ShowSettinsScreen() {
//    SettingsScreen(
//        SettingViewModel(
//            SettingsRepository(context)
//        )
//    )
}