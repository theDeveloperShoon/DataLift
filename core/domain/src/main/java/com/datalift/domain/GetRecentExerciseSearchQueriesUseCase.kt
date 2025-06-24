package com.datalift.domain

import com.datalift.data.model.RecentExerciseSearchQuery
import com.datalift.data.repository.RecentExerciseSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentExerciseSearchQueriesUseCase @Inject constructor(
    private val recentExerciseSearchRepository: RecentExerciseSearchRepository
){
    operator fun invoke(): Flow<List<RecentExerciseSearchQuery>> =
        recentExerciseSearchRepository.getRecentSearchQueries()
}