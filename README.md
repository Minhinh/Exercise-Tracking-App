# KeepTrack - Exercise Tracking App

## Overview

An Android application designed to help users keep track of their exercises and workouts. The app allows users to add, edit, delete, and view detailed information about their exercises, as well as track their progress over time. It integrates advanced features like search/filtering, LiveData for real-time updates, and displays statistics to help users analyze their workout routines.

## Features

- **Exercise Management**: 
  - Add new exercises, including details such as name, duration, sets, reps, calories burned, and date.
  - Edit or delete existing exercises easily.
  - View a list of all recorded exercises.
- **Search & Filtering**: 
  - Quickly search for exercises by name or date.
- **Statistics**: 
  - Analyze workout routines with visual representations like charts and graphs.
  - View aggregated data such as total calories burned and average exercise duration.
- **User-Friendly Interface**:
  - Intuitive and clean design for easy navigation.
  - Snackbar feedback on important actions (e.g., save, delete, cancel).
- **Real-Time Data Updates**: 
  - Utilizes LiveData and ViewModel for seamless updates to the user interface without manual refreshes.
- **Room Database**: 
  - Persistent data storage to ensure exercise records are saved across app restarts.

## Technologies Used

- **Kotlin**: Core language used for app development.
- **Android SDK**: Utilizes Android development tools and SDK components.
- **Jetpack Compose**: This eliminates the need for XML and offers a more reactive approach to UI development
- **Room Database**: Manages persistent data storage for exercises.
- **LiveData & ViewModel**: Ensures real-time data updates and lifecycle-aware components.
- **RecyclerView**: Displays exercise list with smooth scrolling and complex layouts.

## Screenshots App

![image](https://github.com/user-attachments/assets/b4b88df3-56f4-42cb-be28-78f508794a66)

![image](https://github.com/user-attachments/assets/8887841c-d57d-4637-9d45-7e16f42155de)


## Layouts

### Main Activity Layout
- Displays a list of recorded exercises in a RecyclerView.
- Features a search bar for quick filtering.
- Buttons to add new exercises or view overall statistics.

### Add/Edit Activity Layout
- Form to input exercise details such as name, duration, sets, reps, calories burned, and date.
- Save or cancel changes with intuitive button actions.
- Snackbar confirmation for successful actions.

### Statistics Activity Layout
- Graphical representation of workout data.
- Showcases total calories burned, average duration, and more.
- Easy navigation to switch between various statistics views.

## Data Model

```kotlin
@Parcelize
data class Exercise(
    var name: String,
    var duration: Int,
    var sets: Int,
    var reps: Int,
    var caloriesBurned: Int,
    var date: String
) : Parcelable
