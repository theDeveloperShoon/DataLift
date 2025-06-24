package com.datalift.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(
    tableName = "recentExerciseSearchQueries"
)
data class RecentExerciseSearchQueryEntity(
    @PrimaryKey val query: String,
    @ColumnInfo val queriedDate: Instant,
)
