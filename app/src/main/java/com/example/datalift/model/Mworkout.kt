package com.example.datalift.model

import com.google.firebase.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId

data class Mworkout(
    var name: String = "",
    val date: Timestamp = Timestamp.now(),
    var muscleGroup: String = "",
    var docID: String = "",
    var exercises: List<Mexercise> = emptyList()
) {
    private fun toLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofInstant(date.toDate().toInstant(), ZoneId.systemDefault())
    }

    // Get formatted date string
    fun getFormattedDate(): String {
        val localDateTime = toLocalDateTime()
        return "${localDateTime.month} ${localDateTime.dayOfMonth}, ${localDateTime.year}"
    }

    // Check if the workout has exercises
//    fun hasExercises(): Boolean {
//        return exercises.isNotEmpty()
//    }
}
