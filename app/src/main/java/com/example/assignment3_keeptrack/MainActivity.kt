package com.example.assignment3_keeptrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment3_keeptrack.AddExerciseActivity
import com.example.assignment3_keeptrack.Exercise
import com.example.assignment3_keeptrack.ExerciseAdapter
import com.example.assignment3_keeptrack.ExerciseDao
import com.example.assignment3_keeptrack.ExerciseDatabase
import com.example.assignment3_keeptrack.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_EXERCISE = "com.example.assignment3_keeptrack.EXTRA_EXERCISE"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var exerciseAdapter: ExerciseAdapter
    private val exerciseList = mutableListOf<Exercise>()
    private lateinit var exerciseDao: ExerciseDao

    private val addExerciseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val exercise = result.data?.getParcelableExtra<Exercise>(EXTRA_EXERCISE)
                exercise?.let {
                    // Add the new exercise to the database
                    saveExercise(it)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ActivityLifecycle", "MainActivity onCreate called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Room database
        val exerciseDatabase = ExerciseDatabase.getDatabase(applicationContext)
        exerciseDao = exerciseDatabase.exerciseDao()
        Log.d("ExerciseDatabase", "Database initialized.")

        // Set up RecyclerView
        setupRecyclerView()

        // Fetch exercises from the database
        fetchExercisesFromDatabase()

        // Launch AddExerciseActivity
        binding.addExerciseButton.setOnClickListener {
            val intent = Intent(this, AddExerciseActivity::class.java)
            Log.d("ActivityLifecycle", "Launching AddExerciseActivity")
            addExerciseLauncher.launch(intent)
        }
    }



    private fun setupRecyclerView() {
        exerciseAdapter = ExerciseAdapter(exerciseList) {
            // Handle item clicks if needed (e.g., edit or view details)
        }
        binding.exerciseRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = exerciseAdapter
        }
    }

    private fun saveExercise(exercise: Exercise) {
        Log.d("ExerciseDatabase", "Attempting to save exercise: $exercise")
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                exerciseDao.insert(exercise)
                Log.d("ExerciseDatabase", "Inserted exercise: $exercise")
                fetchExercisesFromDatabase()
            } catch (e: Exception) {
                Log.e("ExerciseDatabase", "Error inserting exercise: ${e.message}")
            }
        }
    }


    private fun fetchExercisesFromDatabase() {
        Log.d("ExerciseDatabase", "Attempting to fetch exercises from database.")
        lifecycleScope.launch {
            val exercises = withContext(Dispatchers.IO) {
                exerciseDao.getAllExercises()
            }
            Log.d("ExerciseDatabase", "Fetched exercises: $exercises")
            exerciseList.clear()
            exerciseList.addAll(exercises)
            exerciseAdapter.notifyDataSetChanged()
        }
    }


}
