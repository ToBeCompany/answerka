package com.arbonik.answerka.viewmodels

actual class SettingsRepository {
    actual fun getKey(): String? = null

    actual fun saveKey(key: String) {}

    actual fun clearKey() {}
}