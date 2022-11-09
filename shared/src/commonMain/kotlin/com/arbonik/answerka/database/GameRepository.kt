package com.arbonik.answerka.database

import com.arbonik.answerka.database.data.AskDb
import com.arbonik.answerka.database.data.TaskDb
import com.arbonik.answerka.entity.Ask
import com.arbonik.answerka.entity.DataSetting
import com.arbonik.answerka.entity.Task
import io.realm.kotlin.ext.query


class GameRepository(
    private val realmDatabase: RealmDatabase,
) {
    // На каждую игру осуществляется предзагрузка подходящих данных в оперативную память
    // после чего они фильтруются на неповторяемость с помощью удаления из общего списка

    var dataSetting: DataSetting = DataSetting(false, false)

    private val tasks = mutableListOf<Task>()
    private val asks = mutableListOf<Ask>()

    init {
        loadFromDb()
    }

    private fun loadFromDb() {
        tasks.clear()
        tasks.addAll(
            realmDatabase.realm.query<TaskDb>().apply {
                if (dataSetting.with18)
                    query("is18 = true")
            }
                .find()
                .map { t ->
                    Task(t.text, t.is18)
                }
        )
        asks.clear()
        asks.addAll(
            realmDatabase.realm.query<AskDb>().apply {
                if (dataSetting.with18)
                    query("is18 = true")
            }
                .find()
                .map { t ->
                    Ask(t.text, t.is18)
                }.toMutableList()
        )
    }

    fun getTask(): Task? = getItem(tasks)

    fun getAsk(): Ask? = getItem(asks)

    private fun <T> getItem(list: MutableList<T>): T? =
        if (list.isEmpty())
            null
        else
            if (dataSetting.repeat)
                list.random()
            else
                list.removeAt(list.indices.random())
}

