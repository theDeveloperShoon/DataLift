package com.datalift.data.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUserId(): String
//    fun getUser(id: String) : Flow<>
}