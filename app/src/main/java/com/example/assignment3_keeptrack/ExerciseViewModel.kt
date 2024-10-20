package com.example.assignment3_keeptrack

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val exerciseDao = ExerciseDatabase.getDatabase(application).exerciseDao()
    private val repository = ExerciseRepository(exerciseDao)

    // Expose LiveData to the UI
    val allExercises: LiveData<List<Exercise>> = repository.getAllExercises()

    fun insert(exercise: Exercise) {
        viewModelScope.launch {
            repository.insert(exercise)
        }
    }

    fun update(exercise: Exercise) {
        viewModelScope.launch {
            repository.update(exercise)
        }
    }

    fun delete(exercise: Exercise) {
        viewModelScope.launch {
            repository.delete(exercise)
        }
    }
}
