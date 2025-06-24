package com.datalift.data.repository

import com.datalift.data.model.RecentExerciseSearchQuery
import kotlinx.coroutines.flow.Flow

interface RecentExerciseSearchRepository {
    fun getRecentSearchQueries(): Flow<List<RecentExerciseSearchQuery>>

    suspend fun insertOrReplaceRecentSearch(searchQuery: String)

    suspend fun deleteAllRecentSearches()
}