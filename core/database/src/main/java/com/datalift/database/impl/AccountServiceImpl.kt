package com.datalift.database.impl

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import com.datalift.common.Result
import com.datalift.database.service.AccountService
import com.datalift.model.data.User
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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

    override suspend fun createUserEmailAndPassword(
        email: String,
        password: String
    ): Flow<Result<Unit>> {
        Log.d("SIGNUP", "ServiceImpl called")
        return callbackFlow {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("SIGNUP", "User created")
                        trySend(Result.Success(Unit))
                    } else {
                        Log.d("SIGNUP", "User not created")
                        trySend(Result.Error(task.exception ?: Exception("Unknown Error")))
                    }
                }
            awaitClose {
                Log.d("SIGNUP", "Flow closed")
            }
//                .addOnSuccessListener {
//                    Log.d("SIGNUP", "User created")
//                    trySend(Result.Success(Unit))
//                }
//                .addOnFailureListener {
//                    Log.d("SIGNUP", "User not created")
//                    trySend(Result.Error(it))
//                }
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Result<Unit>> {
        return callbackFlow {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    trySend(Result.Success(Unit))
                }
                .addOnFailureListener {
                    trySend(Result.Error(it))
                }
        }
    }

    override suspend fun signInWithGoogle(credential: Credential): Flow<Result<Unit>> {
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
            return callbackFlow {
                Log.d("LOGIN", "Firebase Credential received")
                val firebaseCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)
                    .let{ googleIdTokenCredential ->
                        Log.d("LOGIN", "Firebase Credential created")
                        GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                    }

                Log.d("LOGIN", "Firebase Credential passed")

                auth.signInWithCredential(firebaseCredential)
                    .addOnSuccessListener {
                        trySend(Result.Success(Unit))
                    }
                    .addOnFailureListener {
                        trySend(Result.Error(it))
                    }
            }
        }
        return callbackFlow {
            trySend(Result.Error(Exception("Invalid Credential")))
        }
    }

    override suspend fun signOut() {
        auth.signOut()

//        try {
//           val clearRequest = ClearCredentialStateRequest()
//
//        } catch (){
//
//        }
    }
}