package com.example.assignment3_keeptrack

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.assignment3_keeptrack.databinding.ActivityStatisticsBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class StatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var viewModel: ExerciseViewModel
    private lateinit var exerciseBarChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)

        // Initialize BarChart
        exerciseBarChart = binding.exerciseBarChart

        // Observe LiveData from ViewModel
        observeStatistics()

        // Set up the back button click listener
        binding.backButton.setOnClickListener {
            finish() // This will close the current activity and return to the previous one
        }
    }

    private fun observeStatistics() {
        viewModel.allExercises.observe(this, Observer { exercises ->
            Log.d("StatisticsActivity", "Fetched exercises: $exercises")
            updateStatistics(exercises)
        })
    }

    private fun updateStatistics(exercises: List<Exercise>) {
        val totalCalories = exercises.sumOf { it.caloriesBurned }
        val averageDuration = if (exercises.isNotEmpty()) {
            exercises.sumOf { it.duration } / exercises.size
        } else {
            0
        }
        val exerciseCount = exercises.size

        binding.totalCaloriesTextView.text = "Total Calories Burned: $totalCalories"
        binding.averageDurationTextView.text = "Average Duration: $averageDuration minutes"
        binding.exerciseCountTextView.text = "Total Exercises: $exerciseCount"

        // Prepare data for the BarChart
        setupBarChart(exercises)
    }

    private fun setupBarChart(exercises: List<Exercise>) {
        val barEntries = ArrayList<BarEntry>()
        exercises.forEachIndexed { index, exercise ->
            barEntries.add(BarEntry(index.toFloat(), exercise.caloriesBurned.toFloat()))
        }

        val barDataSet = BarDataSet(barEntries, "Calories Burned")
        barDataSet.color = resources.getColor(R.color.colorPrimary, theme) // Change color as desired

        // Disable value labels on the bars
        barDataSet.setDrawValues(false) // Set to false to remove labels from the bars

        val barData = BarData(barDataSet)
        exerciseBarChart.data = barData

        // Set X-axis labels
        val xLabels = exercises.map { it.name } // Assuming Exercise has a 'name' property
        exerciseBarChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return if (value.toInt() < xLabels.size) xLabels[value.toInt()] else ""
            }
        }

        // Prevent duplicate x-values
        exerciseBarChart.xAxis.granularity = 1f // Only show labels for each exercise

        // Set the label count
        exerciseBarChart.xAxis.setLabelCount(exercises.size, true) // Limit the number of labels displayed

        // Position the X-axis at the bottom
        exerciseBarChart.xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM

        exerciseBarChart.invalidate() // Refresh the chart
    }


}
