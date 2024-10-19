package com.example.assignment3_keeptrack

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize // Importing the Parcelize annotation

@Entity(tableName = "exercise")
@Parcelize
data class Exercise(
    var name: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) : Parcelable
