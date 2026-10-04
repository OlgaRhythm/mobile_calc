package com.example.mobile_calc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mobile_calc.ui.CalculatorScreen
import com.example.mobile_calc.ui.theme.MobileCalcTheme

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
