package com.datalift.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.datalift.database.model.RecentExerciseSearchQueryEntity
import com.datalift.database.room.dao.RecentExerciseSearchQueryDao

@Database(
    entities = [
        RecentExerciseSearchQueryEntity::class
   ],
    version = 1,
)
internal abstract class DataliftDatabase : RoomDatabase() {
    abstract fun recentExerciseSearchQueryDao(): RecentExerciseSearchQueryDao
}