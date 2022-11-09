package com.arbonik.answerka.database

import com.arbonik.answerka.entity.Ask
import com.arbonik.answerka.entity.DataSetting
import com.arbonik.answerka.entity.Task

class TestGameRepository : GameRepository {
    override var dataSetting: DataSetting = DataSetting(true, true)

    private val tasks = listOf(
        Task("Подпрыгни с вывертом", false),
        Task("Достань пяткой до лба соседа", true),
        Task("Очнись", false),
    )

    private val asks = listOf(
        Ask("как дела", false),
        Ask("Ты пес?", false),
        Ask("Зачем ты дышишь", false),
    )

    override fun getTask(): Task = tasks.random()

    override fun getAsk(): Ask = asks.random()
}