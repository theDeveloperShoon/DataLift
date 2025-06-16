package com.datalift.database.impl

import com.datalift.database.service.AccountService
import com.datalift.database.service.WorkoutService
import com.datalift.model.data.WorkoutResource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WorkoutServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : WorkoutService {
    override fun getLoggedWorkoutsForUser(): Flow<List<WorkoutResource>> {
        return callbackFlow {
            firestore.collection("Users")
                .document(auth.currentUserId)
                .collection("Workouts")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { snapShot ->
                    val workoutList = mutableListOf<WorkoutResource>()
                    for (document in snapShot.documents) {
                        val workout = document.toObject<WorkoutResource>()
                        if (workout != null) {
                            workoutList.add(workout)
                        }
                    }
                    trySend(workoutList.toList())
                }.addOnFailureListener {
                   cancel("Failed to retrieve")
                }
        }
    }

    override fun getWorkout(workoutId: String): Flow<WorkoutResource> {
        return callbackFlow {
            firestore.collection("Users")
                .document(auth.currentUserId)
                .collection("Workouts")
                .document(workoutId)
                .get()
                .addOnSuccessListener {
                    val workout = it.toObject<WorkoutResource>()
                    if (workout != null) {
                        trySend(workout)
                    } else {
                        cancel("Failed to parse workout data")
                    }
                }.addOnFailureListener {
                    cancel("Failed to get workout")
                }
        }
    }

    override suspend fun deleteWorkout(workoutId: String) {
        firestore.collection("Users")
            .document(auth.currentUserId)
            .collection("Workouts")
            .document(workoutId)
            .delete()
            .await()
    }
}