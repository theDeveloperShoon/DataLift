package com.datalift.data.repository

import androidx.credentials.Credential
import com.datalift.common.Result
import com.datalift.database.service.AccountService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompositeCredentialsRepository @Inject constructor(
    private val accountService: AccountService
) : CredentialsRepository {
    override suspend fun createUserEmailAndPassword(
        email: String,
        password: String
    ): Flow<Result<Unit>> {
        return accountService.createUserEmailAndPassword(email, password)
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Result<Unit>> =
        accountService.signInWithEmailAndPassword(email, password)


    override suspend fun signInWithGoogle(credential: Credential): Flow<Result<Unit>> =
        accountService.signInWithGoogle(credential)

    override suspend fun signOut() {
        accountService.signOut()
    }
}