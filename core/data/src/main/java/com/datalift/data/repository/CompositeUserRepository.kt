package com.datalift.data.repository

import com.datalift.database.service.AccountService
import com.datalift.model.data.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CompositeUserRepository @Inject constructor(
    private val accountService: AccountService
) : UserRepository {
    override val userData: Flow<UserData>
        get() = accountService.user.map { user ->
            UserData(
                userID = user.id,
                userName = user.name ?: "",
                userProfileUrl = user.profileUrl
            )
        }

    override fun getCurrentUserId(): String =
        accountService.currentUserId
}