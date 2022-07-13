package com.arbonik.answerka

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}