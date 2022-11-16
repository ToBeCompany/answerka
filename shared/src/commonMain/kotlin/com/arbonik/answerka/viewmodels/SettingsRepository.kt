package com.arbonik.answerka.viewmodels

import com.arbonik.answerka.entity.Category

expect class SettingsRepository {
    fun getKey(): String?
    fun saveKey(key: String)
    fun clearKey()

    fun categories():List<Category>
    fun updateCategory(category: Category)
    fun deleteCategory(category: Category)
}
