package com.example.assignment3_keeptrack

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment3_keeptrack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_EXERCISE = "com.example.assignment3_keeptrack.EXTRA_EXERCISE"
    }

    private lateinit var binding: ActivityMainBinding
    private val exerciseList = mutableListOf<Exercise>()
    private lateinit var exerciseAdapter: ExerciseAdapter

    private val addExerciseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val exercise = result.data?.getParcelableExtra<Exercise>(EXTRA_EXERCISE)
                exercise?.let {
                    // Add the new exercise to the list and update RecyclerView
                    exerciseList.add(it)
                    exerciseAdapter.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        setupRecyclerView()

        // Launch AddExerciseActivity
        binding.addExerciseButton.setOnClickListener {
            val intent = Intent(this, AddExerciseActivity::class.java)
            addExerciseLauncher.launch(intent)
        }
    }

    private fun setupRecyclerView() {
        exerciseAdapter = ExerciseAdapter(exerciseList) { exercise ->
            // Handle item clicks if needed (e.g., edit or view details)
        }
        binding.exerciseRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = exerciseAdapter
        }
    }
}
