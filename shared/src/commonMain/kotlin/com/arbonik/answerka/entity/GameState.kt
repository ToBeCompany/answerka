package com.arbonik.answerka.entity

// Показывает состояние игры
// INIT -> начальное значение, добавляются игроки, создаются настройки
// Ask -> загрузился вопрос, игроки отвечают
// Task -> загрузилась задача, игроки выполняют
sealed class GameState {
    object INIT : GameState()

    object Start: GameState()
    data class Ask(val ask: com.arbonik.answerka.entity.Ask) : GameState()
    data class Task(val Task: com.arbonik.answerka.entity.Task) : GameState()
}
