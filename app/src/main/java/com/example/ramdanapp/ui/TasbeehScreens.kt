package com.example.ramdanapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ramdanapp.data.Tasbeeh

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TasbeehViewModel,
    onTasbeehClick: (Int) -> Unit
) {
    val tasbeehs by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Tasbeeh List") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (tasbeehs.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Button(onClick = { viewModel.addSampleTasbeehs() }) {
                        Text("Add Sample Tasbeehs")
                    }
                }
            } else {
                LazyColumn {
                    items(tasbeehs) { tasbeeh ->
                        TasbeehItem(tasbeeh = tasbeeh, onClick = { onTasbeehClick(tasbeeh.id) })
                    }
                }
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = tasbeeh.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Progress: ${tasbeeh.currentCount}/${tasbeeh.targetCount}")
            }
            if (tasbeeh.isCompleted) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completed",
                    tint = Color.Green
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(
    id: Int,
    viewModel: TasbeehViewModel,
    onBack: () -> Unit
) {
    val selectedTasbeeh by viewModel.selectedTasbeeh.collectAsState()

    LaunchedEffect(id) {
        viewModel.getTasbeehById(id)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(selectedTasbeeh?.name ?: "Tasbeeh") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            selectedTasbeeh?.let { tasbeeh ->
                Text(
                    text = "${tasbeeh.currentCount}",
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Goal: ${tasbeeh.targetCount}",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                if (tasbeeh.isCompleted) {
                    Text(
                        text = "Masha'Allah! Target Reached!",
                        color = Color.Green,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                Button(
                    onClick = { viewModel.updateCount() },
                    modifier = Modifier
                        .size(200.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    enabled = !tasbeeh.isCompleted
                ) {
                    Text(text = "TAP", fontSize = 32.sp)
                }
                
                Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
                    Text("Back to List")
                }
            }
        }
    }
}