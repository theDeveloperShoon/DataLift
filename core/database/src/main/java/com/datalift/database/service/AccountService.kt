package com.datalift.database.service

import androidx.credentials.Credential
import com.datalift.common.Result
import com.datalift.model.data.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String

    val loggedIn: Boolean
    val loggedInFlow: Flow<Boolean>

    val user : Flow<User>

    suspend fun createUserEmailAndPassword(email: String, password: String) : Flow<Result<Unit>>
    suspend fun signInWithEmailAndPassword(email: String, password: String) : Flow<Result<Unit>>
    suspend fun signInWithGoogle(credential: Credential) : Flow<Result<Unit>>
    suspend fun signOut()
}