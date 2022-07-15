package com.arbonik.answerka

import android.content.res.Resources

actual class Database {
    private val NO_MORE_DATA = "Данные кончились"

    private val charset = Charsets.UTF_8 //Charset.forName("windows-1251")

    var names = mutableListOf<String>()
    var checkedNames = mutableListOf<String>()

    var statements = mutableListOf<String>()
    var soloTasks = mutableListOf<String>()
    var groupTasks = mutableListOf<String>()
    var universalTasks = mutableListOf<String>()
    var targetTasks = mutableListOf<String>()

    actual fun getSoloTasks(): String {
        val lists = listOf(soloTasks, universalTasks)
            .filter {
                it.isNotEmpty()
            }
        if (lists.isNotEmpty()) {
            val list = lists.random()
            val item = list.random()
            list.remove(item)
            return item
        } else return NO_MORE_DATA
    }

    actual fun getGroupTasks(): String {
        val lists = listOf(groupTasks, universalTasks)
            .filter {
                it.isNotEmpty()
            }
        if (lists.isNotEmpty()) {
            val list = lists.random()
            val item = list.random()
            list.remove(item)
            return item
        } else return NO_MORE_DATA
    }

    fun loadData(resources: Resources) {
        soloTasks = loadSoloTasks(resources).toMutableList()
        targetTasks = loadTargetTasks(resources).toMutableList()
        universalTasks = loadUniversalTasks(resources).toMutableList()
        groupTasks = loadGroupTasks(resources).toMutableList()
        statements = loadStatement(resources).toMutableList()
    }

    private fun loadGroupTasks(resources: Resources) =  loadFile(resources, "groups_tasks.txt")

    private fun loadUniversalTasks(resources: Resources) = loadFile(resources, "univers_tasks.txt")

    private fun loadTargetTasks(resources: Resources) = loadFile(resources, "target_tasks.txt")

    private fun loadSoloTasks(resources: Resources) = loadFile(resources, "solo_tasks.txt")

    private fun loadStatement(resources: Resources) = loadFile(resources, "statements.txt")

    private fun loadFile(resources: Resources, filename: String) =
        resources.assets.open(filename).use {
            it.reader(charset).readLines()
        }
}