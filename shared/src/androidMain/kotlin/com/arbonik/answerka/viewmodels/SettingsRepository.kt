package com.arbonik.answerka.viewmodels

import android.content.Context
import androidx.core.content.edit
import com.arbonik.answerka.entity.Category
import com.arbonik.answerka.entity.DataPack

actual class SettingsRepository(
    private val context: Context
) {
    private val sharedPreference by lazy {
        context.getSharedPreferences("key", Context.MODE_PRIVATE)
    }

    init {
        if (sharedPreference.getBoolean("firstLaunch", true)) {
            sharedPreference.edit {
                putBoolean("firstLaunch", false)
                updateCategory(
                    Category(DataPack.STANDART.category, true)
                )
            }
        }
    }

    actual fun getKey(): String? {
        return sharedPreference.getString("access_key", null)
    }

    actual fun saveKey(key: String) {
        sharedPreference.edit(true) {
            putString("access_key", key)
        }
    }

    actual fun clearKey() {
        sharedPreference.edit {
            remove("access_key")
        }
    }

    actual fun categories(): List<Category> {
        return sharedPreference.getStringSet("categories", setOf())?.map { categoryName ->
            Category(categoryName, sharedPreference.getBoolean(categoryName, true))
        } ?: emptyList()
    }

    actual fun updateCategory(category: Category) {
        sharedPreference.edit(commit = true) {
            putStringSet(
                "categories",
                setOf(category.name).plus(
                    sharedPreference.getStringSet("categories", setOf()) ?: setOf()
                )
            )
            putBoolean(category.name, category.isEnabled)
        }
    }

    actual fun deleteCategory(category: Category) {
        val categories = sharedPreference.getStringSet("categories", setOf())
        sharedPreference.edit(commit = true) {
            putStringSet(
                "categories",
                categories?.minus(category.name)
            )
            remove(category.name)
        }
    }
}