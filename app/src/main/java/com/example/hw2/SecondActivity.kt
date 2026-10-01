package com.example.hw2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import java.util.Locale

class SecondActivity : AppCompatActivity() {

    lateinit var gotText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.second_layout)
        Locale.setDefault(Locale("ru"))

        gotText = findViewById<TextView>(R.id.secondTV)

        val received = intent.getStringExtra("MSG")

        gotText.text = received
    }
}