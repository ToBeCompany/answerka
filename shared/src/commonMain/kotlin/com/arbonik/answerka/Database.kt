package com.arbonik.answerka

expect class Database {
    // задание для одного игрока
    fun getSoloTasks(): String

    // задание для нескольких игроков
    fun getGroupTasks(): String
}
