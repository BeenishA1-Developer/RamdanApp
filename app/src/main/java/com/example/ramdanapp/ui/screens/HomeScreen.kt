package com.example.ramdanapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ramdanapp.data.Tasbeeh
import com.example.ramdanapp.ui.TasbeehViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: TasbeehViewModel, onAddClick: () -> Unit, onTasbeehClick: (Int) -> Unit) {
    val tasbeehs by viewModel.allTasbeehs.collectAsState(initial = emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("Ramadan Tasbeeh") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Tasbeeh")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(tasbeehs) { tasbeeh ->
                TasbeehItem(tasbeeh = tasbeeh, onClick = { onTasbeehClick(tasbeeh.id) })
            }
        }
    }
}

@Composable
fun TasbeehItem(tasbeeh: Tasbeeh, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = tasbeeh.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "${tasbeeh.currentCount} / ${tasbeeh.targetCount}", fontSize = 16.sp)
            }
            if (tasbeeh.isCompleted) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Completed", tint = Color.Green)
            }
        }
    }
}
