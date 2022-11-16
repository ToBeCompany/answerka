package com.arbonik.answerka.core


actual class PromoCodeManager {
    actual fun isActiveCode(string: String): KeyResult = KeyResult.NotCorrect
    actual fun createCode(endTime: Long, dataAccess: List<String>): String = ""
}