package com.example.assignment3_keeptrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var exerciseRecyclerView: RecyclerView
    private lateinit var exerciseAdapter: ExerciseAdapter
    private var exerciseList = mutableListOf<Exercise>()
    private lateinit var exerciseDao: ExerciseDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exerciseDao = ExerciseDatabase.getDatabase(this).exerciseDao()

        exerciseRecyclerView = findViewById(R.id.recycler_view)
        exerciseAdapter = ExerciseAdapter(exerciseList) { exercise -> editExercise(exercise) }
        exerciseRecyclerView.adapter = exerciseAdapter
        exerciseRecyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.add_button).setOnClickListener {
            val intent = Intent(this, AddExerciseActivity::class.java)
            startActivityForResult(intent, ADD_EXERCISE_REQUEST)
        }

        loadExercises()
    }

    private fun loadExercises() {
        lifecycleScope.launch {
            exerciseList.clear()
            exerciseList.addAll(exerciseDao.getAllExercises())
            exerciseAdapter.notifyDataSetChanged()
        }
    }

    private fun editExercise(exercise: Exercise) {
        val intent = Intent(this, EditExerciseActivity::class.java).apply {
            putExtra(EXTRA_EXERCISE, exercise)
        }
        startActivityForResult(intent, EDIT_EXERCISE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val exercise = data?.getParcelableExtra<Exercise>(EXTRA_EXERCISE)
            when (requestCode) {
                ADD_EXERCISE_REQUEST -> exercise?.let {
                    lifecycleScope.launch {
                        exerciseDao.insert(it)
                        loadExercises()
                    }
                }
                EDIT_EXERCISE_REQUEST -> exercise?.let {
                    lifecycleScope.launch {
                        exerciseDao.update(it)
                        loadExercises()
                    }
                }
            }
        }
    }

    companion object {
        const val ADD_EXERCISE_REQUEST = 1
        const val EDIT_EXERCISE_REQUEST = 2
        const val EXTRA_EXERCISE = "com.example.assignment3_keeptrack.EXTRA_EXERCISE"
    }
}
