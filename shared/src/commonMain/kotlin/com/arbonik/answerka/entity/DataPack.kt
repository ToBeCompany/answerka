package com.arbonik.answerka.entity


// TODO перечисление паков вопросов(категорий), чтобы избежать строковых констант по коду
// но если будут добавляться категории удаленно, нужно будет делать другой подход через динамический список
enum class DataPack(val category:String){
    STANDART("standard"),
    PLUS18("is18")
}