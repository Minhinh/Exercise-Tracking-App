package com.example.assignment3_keeptrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment3_keeptrack.MainActivity.Companion.EXTRA_EXERCISE
import com.example.assignment3_keeptrack.databinding.ActivityEditExerciseBinding

class EditExerciseActivity : AppCompatActivity() {
    private lateinit var exercise: Exercise
    private lateinit var binding: ActivityEditExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityEditExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Access views via binding
        exercise = intent.getParcelableExtra(EXTRA_EXERCISE, Exercise::class.java) ?: return
        binding.exerciseNameEdit.setText(exercise.name)
        binding.exerciseDurationEdit.setText(exercise.duration.toString())
        binding.exerciseSetsEdit.setText(exercise.sets.toString())
        binding.exerciseRepsEdit.setText(exercise.reps.toString())
        binding.exerciseCaloriesEdit.setText(exercise.caloriesBurned.toString())

        binding.saveButton.setOnClickListener {
            exercise.name = binding.exerciseNameEdit.text.toString()
            exercise.duration = binding.exerciseDurationEdit.text.toString().toIntOrNull() ?: exercise.duration
            exercise.sets = binding.exerciseSetsEdit.text.toString().toIntOrNull() ?: exercise.sets
            exercise.reps = binding.exerciseRepsEdit.text.toString().toIntOrNull() ?: exercise.reps
            exercise.caloriesBurned = binding.exerciseCaloriesEdit.text.toString().toIntOrNull() ?: exercise.caloriesBurned

            val resultIntent = Intent().apply {
                putExtra(EXTRA_EXERCISE, exercise)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
