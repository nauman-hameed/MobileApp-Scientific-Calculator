package com.example.scientificcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvInput: TextView
    private var input = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)

        // 🧮 Number Buttons
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                appendToInput((it as Button).text.toString())
            }
        }

        // ➕ Basic Operations
        findViewById<Button>(R.id.btnAdd).setOnClickListener { appendToInput("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { appendToInput("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { appendToInput("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { appendToInput("/") }
        findViewById<Button>(R.id.btnDot).setOnClickListener { appendToInput(".") }

        // 🧠 Scientific Operations
        findViewById<Button>(R.id.btnSin).setOnClickListener { appendToInput("sin(") }
        findViewById<Button>(R.id.btnCos).setOnClickListener { appendToInput("cos(") }
        findViewById<Button>(R.id.btnTan).setOnClickListener { appendToInput("tan(") }
        findViewById<Button>(R.id.btnLog).setOnClickListener { appendToInput("log(") }
        findViewById<Button>(R.id.btnLn).setOnClickListener { appendToInput("ln(") }
        findViewById<Button>(R.id.btnSqrt).setOnClickListener { appendToInput("√(") }
        findViewById<Button>(R.id.btnPow).setOnClickListener { appendToInput("^") }
        findViewById<Button>(R.id.btnFact).setOnClickListener { appendToInput("!") }

        // 🧹 Clear & Equal
        findViewById<Button>(R.id.btnClear).setOnClickListener { clearInput() }
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }
    }

    private fun appendToInput(value: String) {
        input += value
        tvInput.text = input
    }

    private fun clearInput() {
        input = ""
        tvInput.text = "0"
    }

    private fun calculateResult() {
        try {
            val result = evaluateExpression(input)
            tvInput.text = result.toString()
            input = result.toString()
        } catch (e: Exception) {
            tvInput.text = "Error"
        }
    }

    // 🧩 Expression Evaluator (Basic + Scientific)
    private fun evaluateExpression(expr: String): Double {
        var expression = expr.replace("√", "sqrt")

        // Handle factorial (!)
        while (expression.contains("!")) {
            val match = Regex("(\\d+)!").find(expression)
            if (match != null) {
                val num = match.groupValues[1].toInt()
                val fact = factorial(num)
                expression = expression.replaceFirst("${num}!", fact.toString())
            } else break
        }

        // Replace ^ with pow(a,b)
        while (expression.contains("^")) {
            val match = Regex("(\\d+(?:\\.\\d+)?)\\^(\\d+(?:\\.\\d+)?)").find(expression)
            if (match != null) {
                val base = match.groupValues[1].toDouble()
                val exp = match.groupValues[2].toDouble()
                val res = base.pow(exp)
                expression = expression.replaceFirst(match.value, res.toString())
            } else break
        }

        // Handle trigonometric and logs
        expression = expression
            .replace(Regex("sin\\((.*?)\\)")) { sin(Math.toRadians(it.groupValues[1].toDouble())).toString() }
            .replace(Regex("cos\\((.*?)\\)")) { cos(Math.toRadians(it.groupValues[1].toDouble())).toString() }
            .replace(Regex("tan\\((.*?)\\)")) { tan(Math.toRadians(it.groupValues[1].toDouble())).toString() }
            .replace(Regex("log\\((.*?)\\)")) { log10(it.groupValues[1].toDouble()).toString() }
            .replace(Regex("ln\\((.*?)\\)")) { ln(it.groupValues[1].toDouble()).toString() }
            .replace(Regex("sqrt\\((.*?)\\)")) { sqrt(it.groupValues[1].toDouble()).toString() }

        // Handle basic arithmetic using eval
        return try {
            SimpleEval().eval(expression)
        } catch (e: Exception) {
            Double.NaN
        }
    }

    private fun factorial(n: Int): Double {
        var result = 1.0
        for (i in 1..n) result *= i
        return result
    }
}

/**
 * Very simple math expression evaluator for + - * / and decimals
 */
class SimpleEval {
    fun eval(expr: String): Double {
        val cleanExpr = expr.replace(Regex("[^0-9+\\-*/().]"), "")
        val engine = javax.script.ScriptEngineManager().getEngineByName("rhino")
        val result = engine.eval(cleanExpr)
        return (result as Number).toDouble()
    }
}
