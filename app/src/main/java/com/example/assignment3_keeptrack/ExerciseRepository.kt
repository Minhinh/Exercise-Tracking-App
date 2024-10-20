package com.example.assignment3_keeptrack

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    suspend fun insert(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.insert(exercise)
        }
    }

    suspend fun getAllExercises(): List<Exercise> {
        return withContext(Dispatchers.IO) {
            exerciseDao.getAllExercises()
        }
    }
}
