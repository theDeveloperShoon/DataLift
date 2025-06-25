package com.datalift.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datalift.database.model.RecentExerciseSearchQueryEntity
import com.datalift.database.room.dao.RecentExerciseSearchQueryDao
import com.datalift.database.room.util.InstantConverter

@Database(
    entities = [
        RecentExerciseSearchQueryEntity::class
   ],
    version = 1,
)
@TypeConverters(
    InstantConverter::class
)
internal abstract class DataliftDatabase : RoomDatabase() {
    abstract fun recentExerciseSearchQueryDao(): RecentExerciseSearchQueryDao
}