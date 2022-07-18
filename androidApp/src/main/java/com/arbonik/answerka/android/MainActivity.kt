package com.arbonik.answerka.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(text = "HELLO")
        }
    }
}

@Preview
@Composable
fun TaskScreen(){
    Text(text = "HELLO")
}
