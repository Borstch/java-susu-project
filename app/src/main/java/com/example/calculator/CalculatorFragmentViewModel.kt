package com.example.calculator

import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorFragmentViewModel : ViewModel() {

    var expression = ""

    var result = 0.0

    var copiedResult = 0.0

    fun evaluateMath() {
        val ex = ExpressionBuilder(
            expression.replace(",", ".")
                .replace("÷", "/")
                .replace("×", "*")
        ).build()
        result = ex.evaluate()
    }

    fun update(str: String) {
        expression += str
    }

    fun clear() {
        expression = ""
        result = 0.0
    }

    fun back() {
        if (expression.isNotEmpty()) expression = expression.substring(0, expression.length - 1)
    }

    fun copyResult() {
        copiedResult = result
    }

}