package com.arbonik.answerka.android.navigation


sealed class AnswerkaNavigation {
    val destinationPath = this::class.simpleName?.uppercase() ?: ""

    object CreateGame : AnswerkaNavigation()
    object Ask : AnswerkaNavigation()
    object Task : AnswerkaNavigation()
    object Main : AnswerkaNavigation()
    object Payment : AnswerkaNavigation()
    object Settings : AnswerkaNavigation()
}

object AnswerkaGraph {
    const val MAIN = "root"
    const val GAME = "game"
}