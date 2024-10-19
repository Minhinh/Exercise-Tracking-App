package com.example.assignment3_keeptrack

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_EXERCISE = "com.example.assignment3_keeptrack.EXTRA_EXERCISE"
    }

    private val addExerciseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val exercise = result.data?.getParcelableExtra(EXTRA_EXERCISE, Exercise::class.java)
                exercise?.let {
                    // Handle the received exercise (add to list, show in RecyclerView, etc.)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Launch AddExerciseActivity
        findViewById<Button>(R.id.add_exercise_button).setOnClickListener {
            val intent = Intent(this, AddExerciseActivity::class.java)
            addExerciseLauncher.launch(intent)
        }
    }
}
