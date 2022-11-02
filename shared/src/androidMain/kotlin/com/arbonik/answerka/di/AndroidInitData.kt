package com.arbonik.answerka.di

import android.content.Context
import com.arbonik.answerka.database.InitData
import com.arbonik.answerka.database.data.AskDb
import com.arbonik.answerka.database.data.TaskDb
import com.arbonik.answerka.entity.Ask
import com.arbonik.answerka.entity.Task
import com.google.gson.Gson
import io.realm.kotlin.MutableRealm

class AndroidInitData(
    private val context: Context
) : InitData {
    private val gson = Gson()

    private val charset = Charsets.UTF_8

    private fun loadTasks(): TaskList = loadFromAsset("tasks.txt")
    private fun loadAsks(): AskList = loadFromAsset("asks.txt")

    private inline fun <reified T> loadFromAsset(fileName: String) : T {
        return context.resources.assets.open(fileName).use {
            gson.fromJson(it.reader(charset).readText(), T::class.java)
        }
    }

    override fun loadDataFromAsset(realm: MutableRealm) {
        loadTasks().forEach {
            realm.copyToRealm(TaskDb(it.text, it.is18))
        }
        loadAsks().forEach {
            realm.copyToRealm(AskDb(it.text, it.is18))
        }
    }
}