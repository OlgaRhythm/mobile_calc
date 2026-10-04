package com.example.mobile_calc

import android.os.Parcelable
import java.util.Locale
import kotlin.math.abs
import kotlinx.parcelize.Parcelize

enum class Operator(val symbol: String) {
    ADD("+"), SUB("−"), MUL("×"), DIV("÷")
}

@Parcelize
data class CalculatorState(
    val currentNumber: String = "0",
    val previousNumber: Double? = null,
    val operator: Operator? = null,
    val startNewNumber: Boolean = true,
    val isError: Boolean = false
) : Parcelable {

    val display: String
        get() = when {
            operator == null -> currentNumber
            startNewNumber -> "${formatNumber(previousNumber ?: 0.0)} ${operator.symbol}"
            else -> "${formatNumber(previousNumber ?: 0.0)} ${operator.symbol} $currentNumber"
        }

    fun onDigit(digit: Char): CalculatorState {
        if (isError) return CalculatorState().onDigit(digit)
        val newNumber = if (startNewNumber) digit.toString() else currentNumber + digit
        return copy(currentNumber = newNumber, startNewNumber = false)
    }

    fun onDecimalPoint(): CalculatorState {
        if (isError) return CalculatorState().onDecimalPoint()
        if (startNewNumber) return copy(currentNumber = "0.", startNewNumber = false)
        if (currentNumber.contains('.')) return this
        return copy(currentNumber = "$currentNumber.", startNewNumber = false)
    }

    fun onOperator(newOperator: Operator): CalculatorState {
        if (isError) return this
        val current = currentNumber.toDoubleOrNull()

        val result = if (operator != null && current != null && !startNewNumber) {
            calculate(operator, previousNumber ?: 0.0, current)
        } else {
            previousNumber ?: current ?: 0.0
        }
        if (result.isNaN() || result.isInfinite()) return CalculatorState(isError = true)

        return CalculatorState(
            currentNumber = formatNumber(result),
            previousNumber = result,
            operator = newOperator,
            startNewNumber = true
        )
    }

    fun onEquals(): CalculatorState {
        if (isError) return this
        val op = operator ?: return this
        val previous = previousNumber ?: return this
        val current = currentNumber.toDoubleOrNull() ?: return this

        val result = calculate(op, previous, current)
        if (result.isNaN() || result.isInfinite()) return CalculatorState(isError = true)

        return CalculatorState(currentNumber = formatNumber(result))
    }

    fun onClearAll(): CalculatorState = CalculatorState()

    fun onClearEntry(): CalculatorState {
        if (isError) return CalculatorState()
        return copy(currentNumber = "0", startNewNumber = true)
    }
}

private fun calculate(operator: Operator, a: Double, b: Double): Double = when (operator) {
    Operator.ADD -> a + b
    Operator.SUB -> a - b
    Operator.MUL -> a * b
    Operator.DIV -> a / b
}

private fun formatNumber(value: Double): String {
    if (value == value.toLong().toDouble() && abs(value) < 1e15) {
        return value.toLong().toString()
    }
    return String.format(Locale.US, "%.10f", value).trimEnd('0').trimEnd('.')
}
