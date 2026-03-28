package com.example.ramdanapp.ui.screens

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ramdanapp.ui.TasbeehViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(id: Int, viewModel: TasbeehViewModel, onBack: () -> Unit) {
    val tasbeeh by viewModel.getTasbeehById(id).collectAsState(initial = null)
    val context = LocalContext.current
    val vibrator = context.getSystemService(Vibrator::class.java)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(tasbeeh?.name ?: "Counter") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            tasbeeh?.let { item ->
                Text(text = "${item.currentCount}", fontSize = 120.sp, fontWeight = FontWeight.Bold)
                Text(text = "Target: ${item.targetCount}", fontSize = 24.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(64.dp))

                Box(
                    modifier = Modifier
                        .size(250.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (item.currentCount < item.targetCount) {
                                // Haptic feedback
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                                } else {
                                    vibrator.vibrate(50)
                                }
                                viewModel.incrementCount(item)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier.fillMaxSize(),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = CardDefaults.cardColors(
                            containerColor = if (item.isCompleted) Color.Green else MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = if (item.isCompleted) "Masha'Allah!" else "TAP",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (item.isCompleted) Color.White else MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}
