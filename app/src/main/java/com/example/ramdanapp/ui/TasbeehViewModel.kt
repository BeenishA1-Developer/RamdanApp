package com.example.ramdanapp.ui

import androidx.lifecycle.*
import com.example.ramdanapp.data.Tasbeeh
import com.example.ramdanapp.data.TasbeehRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TasbeehViewModel(private val repository: TasbeehRepository) : ViewModel() {

    val allTasbeehs: Flow<List<Tasbeeh>> = repository.allTasbeehs

    fun addTasbeeh(name: String, targetCount: Int, reminderTime: Long) {
        viewModelScope.launch {
            repository.insert(Tasbeeh(name = name, targetCount = targetCount, reminderTime = reminderTime))
        }
    }

    fun incrementCount(tasbeeh: Tasbeeh) {
        if (tasbeeh.currentCount < tasbeeh.targetCount) {
            val newCount = tasbeeh.currentCount + 1
            val isCompleted = newCount >= tasbeeh.targetCount
            viewModelScope.launch {
                repository.update(tasbeeh.copy(currentCount = newCount, isCompleted = isCompleted))
            }
        }
    }

    fun getTasbeehById(id: Int): Flow<Tasbeeh?> {
        // Simple way to get it from the flow for this example
        // In a real app, use a dedicated getById in DAO and repository
        return allTasbeehs.map { list -> list.find { it.id == id } }
    }
}

class TasbeehViewModelFactory(private val repository: TasbeehRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasbeehViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TasbeehViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
