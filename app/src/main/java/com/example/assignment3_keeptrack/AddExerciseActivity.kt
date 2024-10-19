package com.example.assignment3_keeptrack

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment3_keeptrack.databinding.ActivityAddExerciseBinding
import com.example.assignment3_keeptrack.MainActivity.Companion.EXTRA_EXERCISE

class AddExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityAddExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val exerciseName = binding.exerciseNameEdit.text.toString()

            // Create a new Exercise object (adjust as needed)
            val newExercise = Exercise(name = exerciseName)

            val resultIntent = Intent().apply {
                putExtra(EXTRA_EXERCISE, newExercise)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
