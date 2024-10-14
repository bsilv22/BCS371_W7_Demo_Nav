package edu.farmingdale.bcs371_w7_demo_nav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.Role.Companion.RadioButton
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.ceil
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment // For Alignment.CenterHorizontally
import androidx.compose.ui.unit.dp // For dp



@Composable
fun PizzaPartyScreen(navController: NavController) {
    var totalPizzas by remember { mutableStateOf(0) }
    var numPeopleInput by remember { mutableStateOf("") }
    var hungerLevel by remember { mutableStateOf("Medium") }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(), // Ensure it fills the available space
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pizza Party",
            fontSize = 38.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Number input field
        NumberField(
            labelText = "Number of people?",
            textInput = numPeopleInput,
            onValueChange = { numPeopleInput = it },
            modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth()
        )

        // Radio buttons for hunger level
        RadioGroup(
            labelText = "How hungry?",
            radioOptions = listOf("Light", "Medium", "Very hungry"),
            selectedOption = hungerLevel,
            onSelected = { hungerLevel = it },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display total pizzas
        Text(
            text = "Total pizzas: $totalPizzas",
            fontSize = 22.sp,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )

        // Calculate button
        Button(
            onClick = {
                if (numPeopleInput.isNotEmpty()) {
                    totalPizzas = calculateNumPizzas(numPeopleInput.toInt(), hungerLevel)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }

        // Button to navigate to the GPA calculator screen
        Button(
            onClick = { navController.navigate("gpa_calculator_screen") },
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
        ) {
            Text("Go to GPA Calculator")
        }
    }
}
@Composable
fun NumberField(
    labelText: String,
    textInput: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = textInput,
        onValueChange = onValueChange,
        label = { Text(labelText) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

@Composable
fun RadioGroup(
    labelText: String,
    radioOptions: List<String>,
    selectedOption: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelectedOption: (String) -> Boolean = { selectedOption == it }

    Column(modifier = modifier) {
        Text(labelText)
        radioOptions.forEach { option ->
            Row(
                modifier = Modifier
                    .selectable(
                        selected = isSelectedOption(option),
                        onClick = { onSelected(option) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = isSelectedOption(option),
                    onClick = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = option)
            }
        }
    }
}

fun calculateNumPizzas(numPeople: Int, hungerLevel: String): Int {
    val slicesPerPizza = 8
    val slicesPerPerson = when (hungerLevel) {
        "Light" -> 2
        "Medium" -> 3
        else -> 4
    }
    return ceil(numPeople * slicesPerPerson / slicesPerPizza.toDouble()).toInt()
}