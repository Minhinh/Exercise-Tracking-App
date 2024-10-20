package com.example.assignment3_keeptrack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment3_keeptrack.databinding.ActivityAddExerciseBinding
import com.google.android.material.snackbar.Snackbar // Import Snackbar

class AddExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize View Binding
        binding = ActivityAddExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val exerciseName = binding.exerciseNameEdit.text.toString().trim()
            val duration = binding.durationEdit.text.toString().toIntOrNull() ?: 0
            val sets = binding.setsEdit.text.toString().toIntOrNull() ?: 0
            val reps = binding.repsEdit.text.toString().toIntOrNull() ?: 0
            val caloriesBurned = binding.caloriesEdit.text.toString().toIntOrNull() ?: 0
            val difficulty = binding.difficultySpinner.selectedItem.toString()
            val date = binding.dateEdit.text.toString().trim() // Get date input

            // Validate inputs
            if (exerciseName.isEmpty()) {
                showSnackbar("Please enter a valid exercise name.")
                return@setOnClickListener
            }

            if (duration <= 0 || sets <= 0 || reps <= 0) {
                showSnackbar("Duration, sets, and reps should be greater than 0.")
                return@setOnClickListener
            }

            // Create a new Exercise object
            val newExercise = Exercise(
                name = exerciseName,
                duration = duration,
                sets = sets,
                reps = reps,
                caloriesBurned = caloriesBurned,
                date = date, // Set the date if needed
                difficulty = difficulty
            )

            // Return the new exercise to the MainActivity
            val resultIntent = Intent().apply {
                putExtra(MainActivity.EXTRA_EXERCISE, newExercise)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        // Cancel button click listener
        binding.cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish() // Finish the activity and return to MainActivity
        }
    }
    // Function to show Snackbar messages
    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}
