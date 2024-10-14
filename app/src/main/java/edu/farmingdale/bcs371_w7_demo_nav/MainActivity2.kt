package edu.farmingdale.bcs371_w7_demo_nav

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.farmingdale.bcs371_w7_demo_nav.ui.theme.BCS371_W7_Demo_NavTheme
import androidx.compose.foundation.layout.width

import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController() // Initialize NavController here
            BCS371_W7_Demo_NavTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "basic_operations_screen", // Starting screen
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("basic_operations_screen") {
                            BasicOperations(navController = navController, name = "Activity 1")
                        }
                        composable("pizza_party_screen") {
                            PizzaPartyScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun gpaappFun() {
    // Your content for Pizza Party Screen
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Testing")
    }
}

@Composable
fun PizzaPartyScreen() {
    // Your content for Pizza Party Screen
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Welcome to the Pizza Party!")
    }
}

@Composable
fun BasicOperations(navController: NavController, name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var isChecked by remember { mutableStateOf(true) }

    Column {
        Spacer(modifier = Modifier.padding(50.dp))
        Button( onClick = {
            val newInt = Intent(Intent.ACTION_VIEW)
            newInt.setData(Uri.parse("geo:0,0?q=Farmingdale State College, NY"))
            context.startActivity(newInt)
        },
            modifier= Modifier.padding(start = 40.dp, end = 40.dp),
            enabled = isChecked) {

            Icon( imageVector = Icons.Default.LocationOn, contentDescription = "Location")
            Text("Show me Farmingdale")
        }
        HorizontalDivider(thickness = DividerDefaults.Thickness)

        Button(
            onClick = {
                val phoneNumber = "tel:123456789" // Replace with the desired number
                val newInt = Intent(Intent.ACTION_VIEW, Uri.parse(phoneNumber))
                context.startActivity(newInt)
            },
            modifier = Modifier.padding(start = 40.dp, end = 40.dp),
            enabled = isChecked // Enable/disable based on switch state
        ) {
            Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone")
            Text("Call Me")
        }

        HorizontalDivider(thickness = DividerDefaults.Thickness)

        Button(
            onClick = {
                Toast.makeText(context, "Navigating to Activity 2", Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context, MainActivity2::class.java)) // Opens MainActivity2
            },
            modifier = Modifier.padding(start = 40.dp, end = 40.dp),
            enabled = isChecked // Enable/disable based on switch state
        ) {
            Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
            Spacer(modifier = Modifier.width(10.dp))
            Text("Go To Activity 2")
        }

        HorizontalDivider(thickness = DividerDefaults.Thickness)

        Switch(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.padding(10.dp),
        )


    }
}

@Preview(showBackground = true)
@Composable
fun BasicOperationsPreview() {
    BCS371_W7_Demo_NavTheme {
        // Pass a dummy NavController for preview purposes
        val navController = rememberNavController()
        BasicOperations(navController = navController, name = "Android")
    }
}

