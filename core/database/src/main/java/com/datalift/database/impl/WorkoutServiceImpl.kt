package com.datalift.database.impl

import android.util.Log
import com.datalift.database.service.AccountService
import com.datalift.database.service.WorkoutService
import com.datalift.model.data.ExerciseResource
import com.datalift.model.data.WorkoutDraft
import com.datalift.model.data.WorkoutResource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.Clock
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
                    Log.d("WorkoutService","Workouts List start")
                    val workoutList = mutableListOf<WorkoutResource>()
                    for (document in snapShot.documents) {
                        val workout = document.toObject<WorkoutResource>()
                        if (workout != null) {
                            workoutList.add(workout)
                        }
                    }
                    Log.d("WorkoutService","Workouts List sent")
                    trySend(workoutList.toList())
                }.addOnFailureListener {
                    Log.d("WorkoutService","Failure")
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

    override fun queryExercise(query: String): Flow<List<ExerciseResource>> {
        if(query.isBlank()){
            return callbackFlow {
                trySend(emptyList())
            }
        }
        return callbackFlow {
            firestore.collection("ExerciseList")
                .whereGreaterThanOrEqualTo("Title", query)
                .whereLessThanOrEqualTo("Title", query + "\uf8ff")
                .limit(10)
                .addSnapshotListener { snapShot, exception ->
                    if (exception != null) {
                        // Handle the error (e.g., log or show a message to the user)
                        Log.e("Firestore",
                            "Error fetching exercises: ${exception.message}"
                        )
                        trySend(emptyList()) // Return an empty list if there's an error
                        return@addSnapshotListener
                    }
                    val exerciseList = mutableListOf<ExerciseResource>()

                    snapShot?.documents?.forEach { document ->
                        val exercise = document.toObject<ExerciseResource>()
                        if (exercise != null) {
                            exerciseList.add(exercise)
                        }
                    }
                    trySend(exerciseList.toList())
                }

        }
    }

    override suspend fun saveWorkout(workout: WorkoutDraft) {
        val newWorkout = WorkoutResource(
            workoutId = "",
            workoutName = workout.workoutName,
            date = Clock.System.now(),
            muscleGroups = workout.muscleGroups.ifEmpty {
                listOf("Unspecified")
            },
            exercises = workout.exercises
        )

        firestore.collection("Users")
            .document(auth.currentUserId)
            .collection("Workouts")
            .add(newWorkout)
            .addOnSuccessListener { documentReference ->
                val generatedId = documentReference.id
                Log.d("Firebase", "Workout created with ID: $generatedId")
                documentReference.update("workoutId", generatedId)
            }.addOnFailureListener {
                Log.d("Firebase", "Error creating workout: ${it.message}")
            }.await()
    }

    override suspend fun saveExistingWorkout(workout: WorkoutResource) {
        if(workout.workoutId.isBlank()) return

        firestore.collection("Users")
            .document(auth.currentUserId)
            .collection("Workouts")
            .document(workout.workoutId)
            .set(workout)
            .await()
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