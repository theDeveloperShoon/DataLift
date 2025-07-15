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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : AccountService{
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val loggedIn: Boolean
        get() = auth.currentUser != null

    override val loggedInFlow: Flow<Boolean>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener {
                this.trySend(auth.currentUser != null)
            }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }


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

    private suspend fun addUserToFirestore(user: User){
        Log.d("LOGIN", "Adding User to Firestore")
        Log.d("LOGIN", "User: $user")
        Log.d("LOGIN", "Auth Id: ${auth.uid}")
        firestore.collection("Users")
            .document(user.id)
            .set(user)
            .await()
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

            awaitClose { Log.d("LOGIN", "Flow closed, User Logged In") }
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

                val authResult = auth.signInWithCredential(firebaseCredential)
                    .addOnFailureListener {
                        trySend(Result.Error(it))
                    }
                    .await()

                Log.d("LOGIN", "Firebase Auth signInWithCredential finished")

                authResult.additionalUserInfo?.isNewUser.let { newUser ->
                    if(newUser == true){
                        addUserToFirestore(
                            authResult.user?.toUser() ?: User()
                        )
                        Log.d("LOGIN", "User added to Firestore")
                    }
                }

                trySend(Result.Success(Unit))

//                    .addOnSuccessListener { authResult ->
//                        authResult.additionalUserInfo?.isNewUser.let { newUser ->
//                            if(newUser == true){
//                                addUserToFirestore(
//                                    authResult.user?.toUser() ?: User()
//                                )
//                            }
//                        }
//                        trySend(Result.Success(Unit))
//                    }
//                    .addOnFailureListener {
//                        trySend(Result.Error(it))
//                    }

                awaitClose { Log.d("LOGIN", "Flow closed, User Logged In") }
            }
        }
        return callbackFlow {
            trySend(Result.Error(Exception("Invalid Credential")))
            awaitClose { Log.d("LOGIN", "Flow closed, User Log-in Failed") }
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

fun FirebaseUser.toUser(): User = User(
    id = this.uid,
    name = this.displayName,
    profileUrl = this.photoUrl.toString()
)