package com.arbonik.answerka.database

import com.arbonik.answerka.entity.Ask
import com.arbonik.answerka.entity.Category
import com.arbonik.answerka.entity.Task

class TestGameRepository : GameRepository {

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

    override fun loadData(category: Category) {

    }

    override fun taskQuantity(category: Category): Int {
        return 0
    }

    override fun askQuantity(category: Category): Int {
        return 0
    }

    override fun getTask(): Task = tasks.random()

    override fun getAsk(): Ask = asks.random()
}