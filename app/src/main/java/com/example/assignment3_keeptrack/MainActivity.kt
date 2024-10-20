package com.example.assignment3_keeptrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment3_keeptrack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_EXERCISE = "com.example.assignment3_keeptrack.EXTRA_EXERCISE"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var viewModel: ExerciseViewModel

    private val addExerciseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val exercise = result.data?.getParcelableExtra(EXTRA_EXERCISE, Exercise::class.java)
                exercise?.let {
                    // Add the new exercise to the database
                    viewModel.insert(it)
                }
            }
        }

    private val editExerciseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val updatedExercise = result.data?.getParcelableExtra(EXTRA_EXERCISE, Exercise::class.java)
                updatedExercise?.let {
                    // Update the exercise in the database
                    viewModel.update(it)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ActivityLifecycle", "MainActivity onCreate called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)

        // Set up RecyclerView
        setupRecyclerView()

        // Observe LiveData from ViewModel
        observeExercises()

        // Launch AddExerciseActivity
        binding.addExerciseButton.setOnClickListener {
            val intent = Intent(this, AddExerciseActivity::class.java)
            Log.d("ActivityLifecycle", "Launching AddExerciseActivity")
            addExerciseLauncher.launch(intent)
        }
    }

    private fun setupRecyclerView() {
        // Initialize the ExerciseAdapter with click and delete actions
        exerciseAdapter = ExerciseAdapter(
            onClick = { exercise ->
                // Handle edit action
                val intent = Intent(this, EditExerciseActivity::class.java).apply {
                    putExtra(EXTRA_EXERCISE, exercise)
                }
                editExerciseLauncher.launch(intent) // Launch EditExerciseActivity
            },
            onDelete = { exercise ->
                // Handle delete action
                viewModel.delete(exercise)
            }
        )

        binding.exerciseRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = exerciseAdapter
        }
    }

    private fun observeExercises() {
        viewModel.allExercises.observe(this, Observer { exercises ->
            Log.d("ExerciseDatabase", "Fetched exercises: $exercises")
            exerciseAdapter.submitList(exercises) // Update adapter with new data
        })
    }
}
