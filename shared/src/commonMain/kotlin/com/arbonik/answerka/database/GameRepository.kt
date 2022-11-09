package com.arbonik.answerka.database

import com.arbonik.answerka.entity.Ask
import com.arbonik.answerka.entity.DataSetting
import com.arbonik.answerka.entity.Task

interface GameRepository {
    var dataSetting: DataSetting
    fun getTask(): Task?
    fun getAsk(): Ask?
}