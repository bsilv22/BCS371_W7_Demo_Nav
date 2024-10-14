package edu.farmingdale.bcs371_w7_demo_nav

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.io.File
import java.io.FileOutputStream
import androidx.compose.ui.platform.LocalContext

@Composable
fun GPAAppFun(navController: NavController) {
    val context = LocalContext.current
    var grade1 by remember { mutableStateOf("") }
    var grade2 by remember { mutableStateOf("") }
    var grade3 by remember { mutableStateOf("") }

    // Declare variables for GPA result and background color
    var gpa by remember { mutableStateOf("") }
    var backColor by remember { mutableStateOf(Color.White) }
    var btnLabel by remember { mutableStateOf("Calculate GPA") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("GPA Calculator", fontSize = 38.sp, modifier = Modifier.padding(bottom = 16.dp))

        TextField(
            value = grade1,
            onValueChange = { grade1 = it },
            label = { Text("Course 1 Grade") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        TextField(
            value = grade2,
            onValueChange = { grade2 = it },
            label = { Text("Course 2 Grade") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        TextField(
            value = grade3,
            onValueChange = { grade3 = it },
            label = { Text("Course 3 Grade") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Button(
            onClick = {

                if (btnLabel == "Calculate GPA") {
                    val gpaVal = calGPA(grade1, grade2, grade3)
                    if (gpaVal != null) {
                        gpa = gpaVal.toString()

                        // Save GPA to file
                        saveGpaToFile(context, gpa)

                        // Change background color based on GPA
                        backColor = when {
                            gpaVal < 60 -> Color.Red
                            gpaVal in 60.0..79.0 -> Color.Yellow
                            else -> Color.Green
                        }
                        btnLabel = "Clear"


                    } else {
                        gpa = "Invalid input"
                    }
                } else {
                    // Reset values
                    grade1 = ""
                    grade2 = ""
                    grade3 = ""
                    gpa = ""
                    backColor = Color.White
                    btnLabel = "Calculate GPA"
                }
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(btnLabel)
        }

        // Display GPA result
        if (gpa.isNotEmpty()) {
            Text(text = "GPA: $gpa", fontSize = 22.sp, modifier = Modifier.padding(16.dp))
        }
    }


}

fun calGPA(grade1: String, grade2: String, grade3: String): Double? {
    return try {
        val grades = listOf(grade1.toDouble(), grade2.toDouble(), grade3.toDouble())
        grades.average()
    } catch (e: NumberFormatException) {
        null // Return null if the input cannot be converted to a number
    }
}

fun saveGpaToFile(context: Context, gpa: String) {
    val fileName = "gpa.txt"
    val file = File(context.filesDir, fileName)
    FileOutputStream(file, true).bufferedWriter().use { writer ->
        writer.appendLine(gpa)
    }
}

