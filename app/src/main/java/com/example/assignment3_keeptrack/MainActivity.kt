package com.example.assignment3_keeptrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment3_keeptrack.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar // Import Snackbar

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
                    Log.d("MainActivity", "New exercise added: $exercise")
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
                    Log.d("MainActivity", "Exercise updated: $updatedExercise")
                    // Update the exercise in the database
                    viewModel.update(it)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "MainActivity onCreate called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
        Log.d("MainActivity", "ViewModel initialized")

        // Set up RecyclerView
        setupRecyclerView()

        // Observe LiveData from ViewModel
        observeExercises()

        // Set up search functionality
        setupSearch()

        // Launch AddExerciseActivity
        binding.addExerciseButton.setOnClickListener {
            Log.d("MainActivity", "Launching AddExerciseActivity")
            val intent = Intent(this, AddExerciseActivity::class.java)
            addExerciseLauncher.launch(intent)
        }

        // Launch StatisticsActivity
        binding.statisticsButton.setOnClickListener {
            Log.d("MainActivity", "Launching StatisticsActivity")
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        // Initialize the ExerciseAdapter with click and delete actions
        exerciseAdapter = ExerciseAdapter(
            onClick = { exercise ->
                // Handle edit action
                Log.d("MainActivity", "Editing exercise: $exercise")
                val intent = Intent(this, EditExerciseActivity::class.java).apply {
                    putExtra(EXTRA_EXERCISE, exercise)
                }
                editExerciseLauncher.launch(intent) // Launch EditExerciseActivity
            },
            onDelete = { exercise ->
                // Handle delete action
                Log.d("MainActivity", "Deleting exercise: $exercise")
                viewModel.delete(exercise)
                // Show Snackbar for successful deletion
                showSnackbar("Exercise deleted successfully.")
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

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener { text ->
            val query = text.toString().lowercase()
            Log.d("MainActivity", "Search query: $query")
            val filteredList = viewModel.allExercises.value?.filter { exercise ->
                exercise.name.lowercase().contains(query) ||
                        exercise.date.contains(query) // Add more filter criteria as needed
            }
            exerciseAdapter.submitList(filteredList)
        }
    }

    // Function to show Snackbar messages
    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}
