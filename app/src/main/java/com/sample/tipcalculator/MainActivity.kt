package com.sample.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.sample.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    var billInput by remember { mutableStateOf("") }
    var dishesInput by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(5f) }

    val dishes = dishesInput.toIntOrNull() ?: 0
    val discountPercent = getDiscount(dishes)

    val bill = billInput.toDoubleOrNull() ?: 0.0
    val tipAmount = bill * tipPercent / 100.0
    val discountAmount = bill * discountPercent / 100.0
    val total = bill + tipAmount - discountAmount

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

    }
}