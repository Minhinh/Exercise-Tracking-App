package com.example.assignment3_keeptrack

import androidx.lifecycle.LiveData

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    // Expose LiveData from DAO
    fun getAllExercises(): LiveData<List<Exercise>> {
        return exerciseDao.getAllExercises() // This should return LiveData
    }

    suspend fun insert(exercise: Exercise) {
        exerciseDao.insert(exercise) // No need for withContext since DAO functions should be called from a ViewModel's coroutine scope
    }

    suspend fun update(exercise: Exercise) {
        exerciseDao.update(exercise)
    }

    suspend fun delete(exercise: Exercise) {
        exerciseDao.delete(exercise)
    }
}
