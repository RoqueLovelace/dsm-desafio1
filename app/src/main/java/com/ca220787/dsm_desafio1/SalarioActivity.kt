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

class SalarioActivity : AppCompatActivity() {

    private val AFP = 0.0725
    private val ISSS = 0.03

    data class Tramo(val desde: Double, val hasta: Double?, val cuotaFija: Double, val porcentaje: Double, val excesoDe: Double)
    private val tramos = listOf(
        Tramo(0.0, 472.0, 0.0, 0.0, 0.0),
        Tramo(472.01, 895.24, 17.67, 0.10, 472.0),
        Tramo(895.25, 2038.10, 60.0, 0.20, 895.24),
        Tramo(2038.11, null, 288.57, 0.30, 2038.10)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_salario)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etEmpleado = findViewById<EditText>(R.id.etEmpleado)
        val etSalario = findViewById<EditText>(R.id.etSalarioBase)
        val tvDetalle = findViewById<TextView>(R.id.tvDetalle)
        val df = DecimalFormat("0.00")

        findViewById<Button>(R.id.btnCalcularSalario).setOnClickListener {
            tvDetalle.text = ""

            val nombre = etEmpleado.text.toString().trim()
            if (nombre.isEmpty()) {
                etEmpleado.error = "Ingrese el nombre"
                return@setOnClickListener
            }

            val salarioTxt = etSalario.text.toString().trim()
            val salarioBase = salarioTxt.toDoubleOrNull()
            if (salarioTxt.isEmpty() || salarioBase == null) {
                etSalario.error = "Ingrese un salario válido"
                return@setOnClickListener
            }
            if (salarioBase <= 0.0) {
                etSalario.error = "Debe ser positivo"
                return@setOnClickListener
            }

            val afp = salarioBase * AFP
            val isss = salarioBase * ISSS

            val baseImponible = salarioBase

            val renta = calcularRenta(baseImponible)
            val totalDescuentos = afp + isss + renta
            val neto = salarioBase - totalDescuentos

            tvDetalle.text =
                "Empleado: $nombre\n\n" +
                        "Salario base: $${df.format(salarioBase)}\n" +
                        "AFP (7.25%):  -$${df.format(afp)}\n" +
                        "ISSS (3%):    -$${df.format(isss)}\n" +
                        "Renta:        -$${df.format(renta)}\n" +
                        "-------------------------\n" +
                        "Total descuentos: $${df.format(totalDescuentos)}\n" +
                        "Salario neto:     $${df.format(neto)}"
        }

        findViewById<Button>(R.id.btnVolver).setOnClickListener { finish() }
    }

    private fun calcularRenta(base: Double): Double {
        val tramo = tramos.firstOrNull { base >= it.desde && (it.hasta == null || base <= it.hasta) } ?: return 0.0
        if (tramo.porcentaje == 0.0) return 0.0
        return tramo.cuotaFija + (base - tramo.excesoDe) * tramo.porcentaje
    }
}