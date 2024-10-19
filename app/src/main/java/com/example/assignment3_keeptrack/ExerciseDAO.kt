package com.example.assignment3_keeptrack

import androidx.room.*

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(exercise: Exercise)

    @Update
    suspend fun update(exercise: Exercise)

    @Delete
    suspend fun delete(exercise: Exercise)

    @Query("SELECT * FROM exercise_table ORDER BY date DESC")
    suspend fun getAllExercises(): List<Exercise>
}
