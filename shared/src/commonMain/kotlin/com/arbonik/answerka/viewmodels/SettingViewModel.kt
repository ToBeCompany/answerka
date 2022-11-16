package com.arbonik.answerka.viewmodels

import com.arbonik.answerka.entity.Category
import kotlinx.coroutines.flow.MutableStateFlow

class SettingViewModel(
    private val settingsRepository: SettingsRepository
) {
    val categories: MutableStateFlow<List<Category?>> = MutableStateFlow(emptyList())

    fun loadSettings() {
        categories.value = settingsRepository.categories()
    }

    fun updateCategory(category: Category) {
        settingsRepository.updateCategory(category)
        loadSettings()
    }
}