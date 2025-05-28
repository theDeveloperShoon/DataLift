package com.datalift.database.service

import com.datalift.model.data.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val loggedIn:  Boolean

    val user : Flow<User>

    suspend fun signOut()
}