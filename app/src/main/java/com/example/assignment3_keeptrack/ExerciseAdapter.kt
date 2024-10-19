package com.example.assignment3_keeptrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(
    private val exercises: List<Exercise>,
    private val onClick: (Exercise) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseName: TextView = view.findViewById(R.id.exercise_name)
        val exerciseDetails: TextView = view.findViewById(R.id.exercise_details)
        val exerciseCalories: TextView = view.findViewById(R.id.exercise_calories)

        init {
            view.setOnClickListener {
                onClick(exercises[bindingAdapterPosition]) // `bindingAdapterPosition` replaces `adapterPosition`
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.exerciseName.text = exercise.name
        holder.exerciseDetails.text = "Duration: ${exercise.duration} mins | Sets: ${exercise.sets} | Reps: ${exercise.reps}"
        holder.exerciseCalories.text = "Calories Burned: ${exercise.caloriesBurned} kcal"
    }

    override fun getItemCount() = exercises.size
}
