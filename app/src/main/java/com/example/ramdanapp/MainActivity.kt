package com.example.ramdanapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.*
import com.example.ramdanapp.data.TasbeehDatabase
import com.example.ramdanapp.data.TasbeehRepository
import com.example.ramdanapp.ui.TasbeehViewModel
import com.example.ramdanapp.ui.TasbeehViewModelFactory
import com.example.ramdanapp.ui.screens.AddTasbeehScreen
import com.example.ramdanapp.ui.screens.CounterScreen
import com.example.ramdanapp.ui.screens.HomeScreen
import com.example.ramdanapp.ui.theme.RamdanAppTheme
import com.example.ramdanapp.worker.DailyResetWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = TasbeehDatabase.getDatabase(applicationContext)
        val repository = TasbeehRepository(database.tasbeehDao())
        
        scheduleDailyReset()

        setContent {
            RamdanAppTheme {
                val viewModel: TasbeehViewModel = viewModel(factory = TasbeehViewModelFactory(repository))
                TasbeehApp(viewModel)
            }
        }
    }

    private fun scheduleDailyReset() {
        val workRequest = PeriodicWorkRequestBuilder<DailyResetWorker>(24, TimeUnit.HOURS)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 15, TimeUnit.MINUTES)
            .build()
        
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_reset_work",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}

@Composable
fun TasbeehApp(viewModel: TasbeehViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate("add") },
                onTasbeehClick = { id -> navController.navigate("counter/$id") }
            )
        }
        composable("add") {
            AddTasbeehScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "counter/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            CounterScreen(
                id = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
