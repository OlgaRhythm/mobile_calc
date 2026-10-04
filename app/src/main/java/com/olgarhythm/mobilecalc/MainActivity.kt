package com.olgarhythm.mobilecalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.olgarhythm.mobilecalc.ui.CalculatorScreen
import com.olgarhythm.mobilecalc.ui.theme.MobileCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileCalcTheme {
                CalculatorScreen()
            }
        }
    }
}
