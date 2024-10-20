package com.example.assignment3_keeptrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment3_keeptrack.MainActivity.Companion.EXTRA_EXERCISE
import com.example.assignment3_keeptrack.databinding.ActivityEditExerciseBinding
import com.google.android.material.snackbar.Snackbar

class EditExerciseActivity : AppCompatActivity() {
    private lateinit var exercise: Exercise
    private lateinit var binding: ActivityEditExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("EditExerciseActivity", "EditExerciseActivity onCreate called")

        // Initialize View Binding
        binding = ActivityEditExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Access views via binding
        exercise = intent.getParcelableExtra(EXTRA_EXERCISE, Exercise::class.java) ?: return
        Log.d("EditExerciseActivity", "Editing exercise: $exercise")
        binding.exerciseNameEdit.setText(exercise.name)
        binding.exerciseDurationEdit.setText(exercise.duration.toString())
        binding.exerciseSetsEdit.setText(exercise.sets.toString())
        binding.exerciseRepsEdit.setText(exercise.reps.toString())
        binding.exerciseCaloriesEdit.setText(exercise.caloriesBurned.toString())
        binding.dateEdit.setText(exercise.date) // Set the date in the EditText

        binding.saveButton.setOnClickListener {
            Log.d("EditExerciseActivity", "Save button clicked")
            exercise.name = binding.exerciseNameEdit.text.toString()
            exercise.duration = binding.exerciseDurationEdit.text.toString().toIntOrNull() ?: exercise.duration
            exercise.sets = binding.exerciseSetsEdit.text.toString().toIntOrNull() ?: exercise.sets
            exercise.reps = binding.exerciseRepsEdit.text.toString().toIntOrNull() ?: exercise.reps
            exercise.caloriesBurned = binding.exerciseCaloriesEdit.text.toString().toIntOrNull() ?: exercise.caloriesBurned
            exercise.date = binding.dateEdit.text.toString().trim() // Get the updated date

            val resultIntent = Intent().apply {
                putExtra(EXTRA_EXERCISE, exercise)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            Log.d("EditExerciseActivity", "Exercise updated: $exercise")
            finish()
        }

        // Cancel button click listener
        binding.cancelButton.setOnClickListener {
            Log.d("EditExerciseActivity", "Cancel button clicked")
            setResult(Activity.RESULT_CANCELED)
            finish() // Finish the activity and return to MainActivity
        }
    }
    // Function to show Snackbar messages
    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}
