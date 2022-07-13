package com.arbonik.answerka

import com.arbonik.answerka.data.OurTask

class Repository(
    private val database: Database
){
    var checkedNames : MutableList<String> = mutableListOf<String>()

    // обертка для задания
    fun getTask(): OurTask {
        if (checkedNames.isEmpty())
            return OurTask(null)
        return when (checkedNames.size) {
            1 -> {
                val task = database.getSoloTasks()
                OurTask(task)
            }
            else -> {
                val task = database.getGroupTasks()
                OurTask(task)
            }
        }
    }
}