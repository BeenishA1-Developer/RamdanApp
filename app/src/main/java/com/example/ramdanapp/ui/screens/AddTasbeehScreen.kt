package com.example.ramdanapp.ui.screens

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ramdanapp.ui.TasbeehViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTasbeehScreen(viewModel: TasbeehViewModel, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var targetCount by remember { mutableStateOf("") }
    var reminderTimeMillis by remember { mutableStateOf(0L) }
    var selectedTimeText by remember { mutableStateOf("Select Reminder Time") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            reminderTimeMillis = calendar.timeInMillis
            selectedTimeText = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add New Tasbeeh") }) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Tasbeeh Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = targetCount,
                onValueChange = { targetCount = it },
                label = { Text("Target Count") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { timePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                Text(text = selectedTimeText)
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    val target = targetCount.toIntOrNull() ?: 0
                    if (name.isNotEmpty() && target > 0) {
                        viewModel.addTasbeeh(name, target, reminderTimeMillis)
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Tasbeeh")
            }
        }
    }
}
