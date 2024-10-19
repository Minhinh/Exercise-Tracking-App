package com.example.assignment3_keeptrack

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val exerciseDao = ExerciseDatabase.getDatabase(application).exerciseDao()

    fun insert(exercise: Exercise) {
        viewModelScope.launch {
            exerciseDao.insert(exercise)
        }
    }

    fun update(exercise: Exercise) {
        viewModelScope.launch {
            exerciseDao.update(exercise)
        }
    }

    fun delete(exercise: Exercise) {
        viewModelScope.launch {
            exerciseDao.delete(exercise)
        }
    }

    suspend fun getAllExercises() = exerciseDao.getAllExercises()
}
