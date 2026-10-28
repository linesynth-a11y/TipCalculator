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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Сумма заказа:", fontSize = 14.sp)
            Spacer(Modifier.width(8.dp))
            OutlinedTextField(
                value = billInput,
                onValueChange = { if (it.length <= 10 && it.all { c -> c.isDigit() || c == '.' }) billInput = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Количество блюд:", fontSize = 14.sp)
            Spacer(Modifier.width(8.dp))
            OutlinedTextField(
                value = dishesInput,
                onValueChange = { if (it.length <= 3 && it.all { c -> c.isDigit() }) dishesInput = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(24.dp))

        Text("Чаевые:", fontSize = 14.sp)
        Slider(
            value = tipPercent,
            onValueChange = { tipPercent = it },
            valueRange = 0f..25f,
            steps = 24, // Шаг 1%
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("0", fontSize = 16.sp)
            Text("25", fontSize = 16.sp)
        }

        Spacer(Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Скидка:", fontSize = 18.sp)
            Spacer(Modifier.width(16.dp))

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DiscountRadioOption("3%", 3, discountPercent)
                DiscountRadioOption("5%", 5, discountPercent)
                DiscountRadioOption("7%", 7, discountPercent)
                DiscountRadioOption("10%", 10, discountPercent)
            }
        }
        Spacer(Modifier.height(32.dp))
        Divider()
        Spacer(Modifier.height(16.dp))

        Text("Чаевые: ${"%.2f".format(tipAmount)} руб.", fontSize = 16.sp)
        Text("Скидка: ${"%.2f".format(discountAmount)} руб.", fontSize = 16.sp)
        Text(
            "Итого:  ${"%.2f".format(total)} руб.",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DiscountRadioOption(label: String, value: Int, selected: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RadioButton(
            selected = selected == value,
            onClick = null
        )
        Text(label, fontSize = 16.sp)
    }
}
fun getDiscount(dishes: Int): Int = when {
    dishes in 1..2  -> 3
    dishes in 3..5  -> 5
    dishes in 6..10 -> 7
    dishes > 10     -> 10
    else            -> 0
}