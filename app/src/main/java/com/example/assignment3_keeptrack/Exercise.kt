package com.example.assignment3_keeptrack

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
// Using Parcelable to pass Exercise objects between activities

@Entity(tableName = "exercise_table")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    var duration: Int,
    var sets: Int,
    var reps: Int,
    var caloriesBurned: Int,
    var date: String = "",
    var difficulty: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(duration)
        parcel.writeInt(sets)
        parcel.writeInt(reps)
        parcel.writeInt(caloriesBurned)
        parcel.writeString(date)
        parcel.writeString(difficulty)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Exercise> {
        override fun createFromParcel(parcel: Parcel): Exercise = Exercise(parcel)
        override fun newArray(size: Int): Array<Exercise?> = arrayOfNulls(size)
    }
}
