package com.datalift.database.room.util

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

internal class InstantConverter {
    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(value: Instant?): Long? =
        value?.toEpochMilliseconds()

}