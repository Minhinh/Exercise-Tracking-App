package com.example.assignment3_keeptrack

import androidx.lifecycle.LiveData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    // Expose LiveData from DAO
    fun getAllExercises(): LiveData<List<Exercise>> {
        return exerciseDao.getAllExercises() // This should return LiveData
    }

    suspend fun insert(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.insert(exercise) // Ensure this runs on a background thread
        }
    }

    suspend fun update(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.update(exercise)
        }
    }

    suspend fun delete(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.delete(exercise)
        }
    }
}
