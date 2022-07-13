package com.arbonik.answerka.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arbonik.answerka.Greeting
import android.widget.TextView
import com.arbonik.answerka.Calc

fun greet(): String {
    return Calc().pow(10).toString()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()
    }
}
