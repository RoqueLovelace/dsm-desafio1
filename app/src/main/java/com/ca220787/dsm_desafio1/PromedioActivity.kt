package com.ca220787.dsm_desafio1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat

class PromedioActivity : AppCompatActivity() {

    private val ponderaciones = doubleArrayOf(0.10, 0.15, 0.20, 0.25, 0.30)
    private val notaMinimaAprobacion = 6.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_promedio)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val notasET = listOf(
            findViewById<EditText>(R.id.etNota1),
            findViewById<EditText>(R.id.etNota2),
            findViewById<EditText>(R.id.etNota3),
            findViewById<EditText>(R.id.etNota4),
            findViewById<EditText>(R.id.etNota5),
        )

        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        findViewById<Button>(R.id.btnCalcular).setOnClickListener {
            tvResultado.text = ""

            val nombre = etNombre.text.toString().trim()
            if (nombre.isEmpty()) {
                etNombre.error = "Ingrese el nombre"
                return@setOnClickListener
            }

            val notas = mutableListOf<Double>()
            for (et in notasET) {
                val n = leerNota(et) ?: return@setOnClickListener
                notas.add(n)
            }

            val promedio = calcularPromedioPonderado(notas.toDoubleArray(), ponderaciones)

            val df = DecimalFormat("0.00") // 2 decimales como pide el enunciado :contentReference[oaicite:2]{index=2}
            val estado = if (promedio >= notaMinimaAprobacion) "Aprobó" else "Reprobó"

            tvResultado.text =
                "Estudiante: $nombre\n" +
                        "Promedio final: ${df.format(promedio)}\n" +
                        "Estado: $estado"
        }

        findViewById<Button>(R.id.btnVolver).setOnClickListener { finish() }
    }

    private fun leerNota(et: EditText): Double? {
        val txt = et.text.toString().trim()
        val n = txt.toDoubleOrNull()
        if (txt.isEmpty() || n == null) {
            et.error = "Ingrese una nota válida"
            return null
        }
        if (n < 0.0 || n > 10.0) {
            et.error = "la nota debe estar entre 0 y 10"
            return null
        }
        et.error = null
        return n
    }

    private fun calcularPromedioPonderado(notas: DoubleArray, pesos: DoubleArray): Double {
        var suma = 0.0
        for (i in notas.indices) suma += notas[i] * pesos[i]
        return suma
    }
}