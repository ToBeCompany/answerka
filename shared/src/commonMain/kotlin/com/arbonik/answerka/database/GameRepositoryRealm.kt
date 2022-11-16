package com.arbonik.answerka.database

import com.arbonik.answerka.database.data.AskDb
import com.arbonik.answerka.database.data.TaskDb
import com.arbonik.answerka.entity.Ask
import com.arbonik.answerka.entity.Category
import com.arbonik.answerka.entity.DataPack
import com.arbonik.answerka.entity.Task
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.BaseRealmObject


class GameRepositoryRealm(
    private val realmDatabase: RealmDatabase,
) : GameRepository {

    private val tasks = mutableListOf<Task>()
    private val asks = mutableListOf<Ask>()
    override fun loadData(category: Category) {
        // удаление, чтобы не было дубликатов вопросов в списках
        // + чтобы удалить данные при выключении категории
        if (category.name == DataPack.PLUS18.category) {
            tasks.removeAll {
                it.is18
            }
            asks.removeAll {
                it.is18
            }
        } else {
            tasks.removeAll {
                it.is18.not()
            }
            asks.removeAll {
                it.is18.not()
            }
        }
        // добавление данных при их включении
        if (category.isEnabled) {
            tasks.addAll(
                load<TaskDb>(category)
                    .map { t ->
                        Task(t.text, t.is18)
                    })
            asks.addAll(
                load<AskDb>(category)
                    .map { t ->
                        Ask(t.text, t.is18)
                    })
        }
    }

    override fun taskQuantity(category: Category): Int {
        return realmDatabase.realm.query<TaskDb>().query(
            "${category.name} = true"
        ).find().count()
    }

    override fun askQuantity(category: Category): Int {
        return realmDatabase.realm.query<AskDb>().query(
            "${category.name} = true"
        ).find().count()
    }

    override fun getTask(): Task? = getItem(tasks)

    override fun getAsk(): Ask? = getItem(asks)

    private inline fun <reified T : BaseRealmObject> load(
        category: Category
    ): List<T> {
        val q = realmDatabase.realm.query<T>()
        return (if (category.name == DataPack.PLUS18.category)
            q.query("is18 = true")
        else
            q).find()
    }

    private fun <T> getItem(list: MutableList<T>): T? =
        if (list.isEmpty())
            null
        else
            if (false) // TODO настройка повторения вопросов
                list.random()
            else
                list.removeAt(list.indices.random())
}

