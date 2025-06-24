package com.datalift.data.model

import com.datalift.database.model.RecentExerciseSearchQueryEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class RecentExerciseSearchQuery(
    val query: String,
    val queriedDate: Instant = Clock.System.now(),
)

fun RecentExerciseSearchQueryEntity.asExternalModel() = RecentExerciseSearchQuery(
    query = query,
    queriedDate = queriedDate,
)
