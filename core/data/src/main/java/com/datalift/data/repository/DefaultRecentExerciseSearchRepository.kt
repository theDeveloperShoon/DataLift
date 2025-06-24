package com.datalift.data.repository

import com.datalift.data.model.RecentExerciseSearchQuery
import com.datalift.data.model.asExternalModel
import com.datalift.database.model.RecentExerciseSearchQueryEntity
import com.datalift.database.room.dao.RecentExerciseSearchQueryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import javax.inject.Inject

internal class DefaultRecentExerciseSearchRepository @Inject constructor(
    private val recentExerciseSearchQueryDao: RecentExerciseSearchQueryDao
) : RecentExerciseSearchRepository{
    override fun getRecentSearchQueries(): Flow<List<RecentExerciseSearchQuery>> =
        recentExerciseSearchQueryDao.getRecentQueryEntities()
            .map { searchQueries ->
                searchQueries.map { it.asExternalModel() }
            }

    override suspend fun insertOrReplaceRecentSearch(searchQuery: String) {
        recentExerciseSearchQueryDao.insertOrReplaceQuery(
            RecentExerciseSearchQueryEntity(
                query = searchQuery,
                queriedDate = Clock.System.now(),
            )
        )
    }

    override suspend fun deleteAllRecentSearches() =
        recentExerciseSearchQueryDao.deleteAllRecentQueries()
}