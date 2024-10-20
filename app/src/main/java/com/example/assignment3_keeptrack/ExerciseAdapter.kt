package com.example.assignment3_keeptrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(
    private val onClick: (Exercise) -> Unit,
    private val onDelete: (Exercise) -> Unit // Lambda for delete action
) : ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {

    inner class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseName: TextView = view.findViewById(R.id.exercise_name)
        val exerciseDetails: TextView = view.findViewById(R.id.exercise_details)
        val exerciseCalories: TextView = view.findViewById(R.id.exercise_calories)
        val exerciseDate: TextView = itemView.findViewById(R.id.exercise_date) // New TextView for date
        private val deleteButton: Button = view.findViewById(R.id.deleteButton) // Add delete button

        init {
            view.setOnClickListener {
                onClick(getItem(bindingAdapterPosition)) // Handle click for editing
            }

            // Handle click for deleting the exercise
            deleteButton.setOnClickListener {
                onDelete(getItem(bindingAdapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position) // Use getItem instead of accessing exercises list directly
        holder.exerciseName.text = exercise.name
        holder.exerciseDetails.text = "Duration: ${exercise.duration} mins | Sets: ${exercise.sets} | Reps: ${exercise.reps}"
        holder.exerciseCalories.text = "Calories Burned: ${exercise.caloriesBurned} kcal"
        holder.exerciseDate.text = exercise.date // Set the date
    }

    class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.id == newItem.id // Assuming 'id' is a unique identifier for Exercise
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem == newItem // Compare contents for equality
        }
    }
}
