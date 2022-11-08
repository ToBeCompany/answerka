package com.arbonik.answerka.android

sealed class AnswerkaNavigation(
    val destinationPath : String
) {
    object Splash : AnswerkaNavigation("splash")
    object Main: AnswerkaNavigation("main")
    object CreateGame : AnswerkaNavigation("createGame")
    object Ask : AnswerkaNavigation("ask")
    object Task : AnswerkaNavigation("task")
}