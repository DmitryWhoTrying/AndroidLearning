package com.example.hw2

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import com.example.hw2.ui.theme.HW2Theme
import org.w3c.dom.Text
import kotlin.text.Regex
import java.util.Locale
import kotlin.jvm.java
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {
    lateinit var inputEditText: EditText
    lateinit var helloText: TextView
    lateinit var secondActivityButton: Button
    lateinit var callButton: ImageButton

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        try {
            setContentView(R.layout.main_layout)
            Locale.setDefault(Locale("ru"))
        }
        catch (ex: Exception){
            Log.e("Main", ex.toString())
        }

        inputEditText = findViewById<EditText>(R.id.inputEditText)
        helloText = findViewById<TextView>(R.id.helloText)
        secondActivityButton = findViewById<Button>(R.id.secondActivityButton)
        callButton = findViewById<ImageButton>(R.id.callButton)

        secondActivityButton.setOnClickListener {
            val text = inputEditText.text.toString()

            val intent = Intent(this, SecondActivity::class.java)

            intent.putExtra("MSG", text)

            startActivity(intent)
        }

        callButton.setOnClickListener {
            try {
                val phoneNumber = inputEditText.text.trim().toString()

                val regex = Regex("""\+?.?\d.?\d{3}.?\d{3}.?\d{4}""")

                if (!regex.matches(phoneNumber)){
                    AlertDialog.Builder(this)
                        .setTitle("Ошибка!")
                        .setMessage("Неправильный формат номера.")
                        .setPositiveButton("ОК") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
                else {
                    val phoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                    startActivity(phoneIntent)
                }
            } catch (ex: Exception) {
                Log.e("Main", ex.toString())
            }
        }
    }
}