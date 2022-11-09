package com.arbonik.answerka.entity

data class DataSetting(
    val with18 : Boolean,
    val paymentAccess : Boolean,
    val repeat : Boolean = false
)