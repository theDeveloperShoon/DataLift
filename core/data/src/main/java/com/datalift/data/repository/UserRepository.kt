package com.datalift.data.repository

import com.datalift.model.data.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userData: Flow<UserData>
    fun getCurrentUserId(): String
//    fun getUser(id: String) : Flow<>
}