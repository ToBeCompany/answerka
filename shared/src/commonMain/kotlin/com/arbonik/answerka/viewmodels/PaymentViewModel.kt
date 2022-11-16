package com.arbonik.answerka.viewmodels

import com.arbonik.answerka.core.KeyResult
import com.arbonik.answerka.core.PromoCodeManager
import com.arbonik.answerka.database.GameRepository
import com.arbonik.answerka.entity.Category
import com.arbonik.answerka.entity.DataPack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlin.time.Duration.Companion.days

class PaymentViewModel(
    private val promoCodeManager: PromoCodeManager,
    private val gameRepository: GameRepository,
    private val settingsRepository: SettingsRepository
) {

    val askQuantity = gameRepository.askQuantity(
        Category(
            DataPack.PLUS18.category, true
        )
    )

    val taskQuantity = gameRepository.taskQuantity(
        Category(
            DataPack.PLUS18.category, true
        )
    )

    private val _currentKey : MutableStateFlow<String?> = MutableStateFlow(null)
    val currentKey : StateFlow<String?> = _currentKey.asStateFlow()

    val currentKeyResult : Flow<KeyResult?> = _currentKey
        .map { key ->
            if (key != null) {
                promoCodeManager.isActiveCode(key)
            } else null
        }

    fun activateKey(key : String){
        val isActiveKey = promoCodeManager.isActiveCode(key)
        if (isActiveKey is KeyResult.Success) {
            settingsRepository.saveKey(key)
            settingsRepository.updateCategory(Category(DataPack.PLUS18.category, true))
        }
        if (isActiveKey is KeyResult.Timeout){
            settingsRepository.deleteCategory(Category(DataPack.PLUS18.category, true))
        }
        _currentKey.value = key
    }

    fun loadKey(){
        settingsRepository.getKey()?.let{ key ->
            activateKey(key)
        }
    }

    fun generateKey(){
        _currentKey.value = promoCodeManager.createCode(
            30.days.inWholeMilliseconds,
            listOf("18")
        )
        activateKey(_currentKey.value.toString())
    }

    fun clearKey(){
        settingsRepository.clearKey()
        settingsRepository.deleteCategory(Category(DataPack.PLUS18.category, true))
        _currentKey.value = settingsRepository.getKey()
    }
}