package com.example.assignment3_keeptrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.assignment3_keeptrack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the default fragment
        if (savedInstanceState == null) {
            loadFragment(LogExerciseFragment())
        }

        // Set click listeners for navigation buttons
        binding.btnLogExercise.setOnClickListener {
            loadFragment(LogExerciseFragment())
        }

        binding.btnViewProgress.setOnClickListener {
            loadFragment(ProgressFragment())
        }

        binding.btnWorkoutPlanner.setOnClickListener {
            loadFragment(WorkoutPlannerFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // Replace the current fragment with the selected fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null) // Add to back stack to allow navigating back
        transaction.commit()
    }
}
