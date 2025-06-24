package com.datalift.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.datalift.database.model.RecentExerciseSearchQueryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentExerciseSearchQueryDao {
    @Query("SELECT * FROM recentExerciseSearchQueries ORDER BY queriedDate DESC LIMIT 5")
    fun getRecentQueryEntities(): Flow<List<RecentExerciseSearchQueryEntity>>

    @Upsert
    suspend fun insertOrReplaceQuery(query: RecentExerciseSearchQueryEntity)

//    @Query("DELETE FROM recentExerciseSearchQueries WHERE query = :query")
//    suspend fun deleteQuery(query: String)

    @Query("DELETE FROM recentExerciseSearchQueries")
    suspend fun deleteAllRecentQueries()
}