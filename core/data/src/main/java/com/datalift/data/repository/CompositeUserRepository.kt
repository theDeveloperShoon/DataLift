package com.datalift.data.repository

import com.datalift.database.service.AccountService
import javax.inject.Inject

class CompositeUserRepository @Inject constructor(
    private val accountService: AccountService
) : UserRepository {
    override fun getCurrentUserId(): String =
        accountService.currentUserId
}