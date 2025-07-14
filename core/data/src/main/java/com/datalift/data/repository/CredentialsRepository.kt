package com.datalift.data.repository


import androidx.credentials.Credential
import com.datalift.common.Result
import kotlinx.coroutines.flow.Flow

interface CredentialsRepository {
    suspend fun createUserEmailAndPassword(email: String, password: String) : Flow<Result<Unit>>
    suspend fun signInWithEmailAndPassword(email: String, password: String) : Flow<Result<Unit>>
    suspend fun signInWithGoogle(credential: Credential) : Flow<Result<Unit>>
    suspend fun signOut()
}