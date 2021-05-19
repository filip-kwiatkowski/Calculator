package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var dotUsed = false
    private var numberUsed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        tvInput.append((view as Button).text)
        numberUsed = true
    }

    fun onClear(view: View) {
        tvInput.text = ""
        dotUsed = false
        numberUsed = false
    }

    fun onDecimalPoint(view: View) {
        if (numberUsed && !dotUsed) {
            tvInput.append(".")
            dotUsed = true
            numberUsed = false
        }
    }

    fun onEquals(view: View) {
        if (numberUsed) {
            var tvValue = tvInput.text.toString()
            var prefix = ""
            if (tvValue.startsWith("-")) {
                tvValue = tvValue.substring(1)
                prefix = "-"
            }

            try {
                when {
                    tvValue.contains("-") -> {
                        val splitValue = tvValue.split("-")
                        tvInput.text =
                            removeZeros(((prefix + splitValue[0]).toDouble() - splitValue[1].toDouble()).toString())
                    }
                    tvValue.contains("+") -> {
                        val splitValue = tvValue.split("+")
                        tvInput.text =
                            removeZeros(((prefix + splitValue[0]).toDouble() + splitValue[1].toDouble()).toString())
                    }
                    tvValue.contains("*") -> {
                        val splitValue = tvValue.split("*")
                        tvInput.text =
                            removeZeros(((prefix + splitValue[0]).toDouble() * splitValue[1].toDouble()).toString())
                    }
                    tvValue.contains("/") -> {
                        val splitValue = tvValue.split("/")
                        tvInput.text =
                            removeZeros(((prefix + splitValue[0]).toDouble() / splitValue[1].toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }

    }

    fun onBackspace(view: View) {
        var input = tvInput.text.toString()
        if (input != "") {
            if (input.last() == '.') {
                dotUsed = false
                numberUsed = true
            }
            if (input.last() == '-' || input.last() == '+' || input.last() == '*' || input.last() == '/') {
                if (input.contains(".")) {
                    dotUsed = true
                }
                numberUsed = true
            }
            if(input.length == 1) numberUsed = false
            tvInput.text = input.substring(0, input.length - 1)
        }
    }

    fun onOperator(view: View) {
        if (numberUsed && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.text = removeZeros(tvInput.text.toString())
            tvInput.append((view as Button).text)
            numberUsed = false
            dotUsed = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("+") || value.contains("-")
                    || value.contains("*") || value.contains("/")
        }
    }

    private fun removeZeros(result: String): String {
        var value = result
        while (value.endsWith("0") && value.contains("."))
            value = value.dropLast(1)
        if (value.endsWith("."))
            value = value.dropLast(1)
        return value
    }
}