package com.arbonik.answerka.entity

data class AKeyIos(
    override val workBefore: Long,
    override val order: List<String>
): AKey