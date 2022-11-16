package com.arbonik.answerka.core

import com.arbonik.answerka.entity.AKey

sealed class KeyResult {
    object NotCorrect : KeyResult()
    object Timeout : KeyResult()
    class Success(val aKey: AKey) : KeyResult()
}

expect class PromoCodeManager {
    fun isActiveCode(string: String): KeyResult
    fun createCode(
        endTime: Long,
        dataAccess: List<String>
    ): String
}