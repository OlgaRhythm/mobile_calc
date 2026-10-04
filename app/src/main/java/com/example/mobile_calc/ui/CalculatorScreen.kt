package com.example.mobile_calc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_calc.CalculatorState
import com.example.mobile_calc.Operator
import com.example.mobile_calc.R

@Composable
fun CalculatorScreen() {
    var state by rememberSaveable { mutableStateOf(CalculatorState()) }

    val displayText = if (state.isError) {
        stringResource(R.string.calculator_error)
    } else {
        state.display
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = displayText,
            fontSize = 48.sp,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        CalculatorButtonRow {
            CalculatorButton(stringResource(R.string.button_clear_all), Modifier.weight(1f)) {
                state = state.onClearAll()
            }
            CalculatorButton(stringResource(R.string.button_clear_entry), Modifier.weight(1f)) {
                state = state.onClearEntry()
            }
            Spacer(modifier = Modifier.weight(1f))
            CalculatorButton(stringResource(R.string.button_divide), Modifier.weight(1f)) {
                state = state.onOperator(Operator.DIV)
            }
        }
        CalculatorButtonRow {
            CalculatorButton("7", Modifier.weight(1f)) { state = state.onDigit('7') }
            CalculatorButton("8", Modifier.weight(1f)) { state = state.onDigit('8') }
            CalculatorButton("9", Modifier.weight(1f)) { state = state.onDigit('9') }
            CalculatorButton(stringResource(R.string.button_multiply), Modifier.weight(1f)) {
                state = state.onOperator(Operator.MUL)
            }
        }
        CalculatorButtonRow {
            CalculatorButton("4", Modifier.weight(1f)) { state = state.onDigit('4') }
            CalculatorButton("5", Modifier.weight(1f)) { state = state.onDigit('5') }
            CalculatorButton("6", Modifier.weight(1f)) { state = state.onDigit('6') }
            CalculatorButton(stringResource(R.string.button_subtract), Modifier.weight(1f)) {
                state = state.onOperator(Operator.SUB)
            }
        }
        CalculatorButtonRow {
            CalculatorButton("1", Modifier.weight(1f)) { state = state.onDigit('1') }
            CalculatorButton("2", Modifier.weight(1f)) { state = state.onDigit('2') }
            CalculatorButton("3", Modifier.weight(1f)) { state = state.onDigit('3') }
            CalculatorButton(stringResource(R.string.button_add), Modifier.weight(1f)) {
                state = state.onOperator(Operator.ADD)
            }
        }
        CalculatorButtonRow {
            Spacer(modifier = Modifier.weight(1f))
            CalculatorButton("0", Modifier.weight(1f)) { state = state.onDigit('0') }
            CalculatorButton(stringResource(R.string.button_decimal_point), Modifier.weight(1f)) {
                state = state.onDecimalPoint()
            }
            CalculatorButton(stringResource(R.string.button_equals), Modifier.weight(1f)) {
                state = state.onEquals()
            }
        }
    }
}

@Composable
private fun CalculatorButtonRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = content
    )
}

@Composable
private fun CalculatorButton(label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(64.dp)
            .padding(2.dp)
    ) {
        Text(text = label, fontSize = 20.sp)
    }
}
