package com.arbonik.answerka.database

import com.arbonik.answerka.entity.Ask
import com.arbonik.answerka.entity.Category
import com.arbonik.answerka.entity.Task

interface GameRepository {
    fun loadData(category: Category)
    fun taskQuantity(category: Category):Int
    fun askQuantity(category: Category):Int
    fun getTask(): Task?
    fun getAsk(): Ask?
}