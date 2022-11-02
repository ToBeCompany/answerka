package com.arbonik.answerka.entity

data class Player(val name : String = "")

data class SelectablePlayer(val player: Player, val isSelected : Boolean)