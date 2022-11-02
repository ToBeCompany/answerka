package com.arbonik.answerka.entity

// класс для сохранения данных о сыгранном раунде, для анатилики
// в нем все игроки
// + выбранные игроки
// + текущий вопрос (если скипнут то без задания)
// + список заданий (задание - действие ("замена" и терминальные "пропустить" и "готово")
data class GameSnapshot(
    val player: List<SelectablePlayer>,
    val ask: Ask,
    val tasks: List<TaskToAction>? = null
)

typealias TaskToAction = Pair<Task, TaskAction>
