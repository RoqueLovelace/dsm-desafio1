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
import kotlin.math.pow
import kotlin.math.sqrt

class CalculadoraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculadora)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etNum1 = findViewById<EditText>(R.id.etNum1)
        val etNum2 = findViewById<EditText>(R.id.etNum2)
        val tv = findViewById<TextView>(R.id.tvResultado)
        val df = DecimalFormat("0.####")

        fun n1(): Double? = leerNumero(etNum1)
        fun n2(): Double? = leerNumero(etNum2)

        findViewById<Button>(R.id.btnSuma).setOnClickListener {
            tv.text = ""
            val a = n1() ?: return@setOnClickListener
            val b = n2() ?: return@setOnClickListener
            tv.text = "Resultado: ${df.format(a + b)}"
        }

        findViewById<Button>(R.id.btnResta).setOnClickListener {
            tv.text = ""
            val a = n1() ?: return@setOnClickListener
            val b = n2() ?: return@setOnClickListener
            tv.text = "Resultado: ${df.format(a - b)}"
        }

        findViewById<Button>(R.id.btnMulti).setOnClickListener {
            tv.text = ""
            val a = n1() ?: return@setOnClickListener
            val b = n2() ?: return@setOnClickListener
            tv.text = "Resultado: ${df.format(a * b)}"
        }

        findViewById<Button>(R.id.btnDiv).setOnClickListener {
            tv.text = ""
            val a = n1() ?: return@setOnClickListener
            val b = n2() ?: return@setOnClickListener
            if (b == 0.0) {
                etNum2.error = "No se puede dividir entre 0"
                return@setOnClickListener
            }
            etNum2.error = null
            tv.text = "Resultado: ${df.format(a / b)}"
        }

        findViewById<Button>(R.id.btnPot).setOnClickListener {
            tv.text = ""
            val a = n1() ?: return@setOnClickListener
            val b = n2() ?: return@setOnClickListener
            tv.text = "Resultado: ${df.format(a.pow(b))}"
        }

        findViewById<Button>(R.id.btnRaiz).setOnClickListener {
            tv.text = ""
            val a = n1() ?: return@setOnClickListener
            if (a < 0.0) {
                etNum1.error = "No existe raíz real de negativo"
                return@setOnClickListener
            }
            etNum1.error = null
            tv.text = "Resultado: ${df.format(sqrt(a))}"
        }

        findViewById<Button>(R.id.btnVolver).setOnClickListener { finish() }
    }

    private fun leerNumero(et: EditText): Double? {
        val txt = et.text.toString().trim()
        val n = txt.toDoubleOrNull()
        if (txt.isEmpty() || n == null) {
            et.error = "Ingrese un número válido"
            return null
        }
        et.error = null
        return n
    }
}