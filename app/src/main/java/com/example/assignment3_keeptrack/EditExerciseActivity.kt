package com.example.assignment3_keeptrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment3_keeptrack.databinding.ActivityEditExerciseBinding
import com.example.assignment3_keeptrack.MainActivity.Companion.EXTRA_EXERCISE

class EditExerciseActivity : AppCompatActivity() {
    private lateinit var exercise: Exercise
    private lateinit var binding: ActivityEditExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityEditExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Access views via binding
        exercise = intent.getParcelableExtra(EXTRA_EXERCISE) ?: return
        binding.exerciseNameEdit.setText(exercise.name)

        binding.saveButton.setOnClickListener {
            exercise.name = binding.exerciseNameEdit.text.toString()
            val resultIntent = Intent().apply {
                putExtra(EXTRA_EXERCISE, exercise)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
