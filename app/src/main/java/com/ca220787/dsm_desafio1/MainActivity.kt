package com.ca220787.dsm_desafio1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnPromedio).setOnClickListener {
            startActivity(Intent(this, PromedioActivity::class.java))
        }

        findViewById<Button>(R.id.btnSalario).setOnClickListener {
            startActivity(Intent(this, SalarioActivity::class.java))
        }

        findViewById<Button>(R.id.btnCalculadora).setOnClickListener {
            startActivity(Intent(this, CalculadoraActivity::class.java))
        }
    }
}