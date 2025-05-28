package com.datalift.database.impl

import com.datalift.database.service.AccountService
import com.datalift.model.data.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AccountService{
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val loggedIn: Boolean
        get() = auth.currentUser != null


    override val user: Flow<User>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener {
                this.trySend(auth.currentUser?.let {
                    User(
                        id = it.uid,
                        profileUrl = it.photoUrl?.toString() ?: "",
                        name = it.displayName ?: "",
                    ) 
                } ?: User())
            }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun signOut() {
        auth.signOut()
    }
}