package com.arbonik.answerka.android

sealed class AnswerkaNavigation(
    val destinationPath : String
) {
    object CreateGame : AnswerkaNavigation("createGame")
    object Ask : AnswerkaNavigation("ask")
    object Task : AnswerkaNavigation("task")
}