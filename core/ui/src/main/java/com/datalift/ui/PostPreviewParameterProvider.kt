package com.datalift.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.datalift.model.data.Exercise
import com.datalift.model.data.ExerciseResource
import com.datalift.model.data.ExerciseSet
import com.datalift.model.data.MuscleGroup
import com.datalift.model.data.Post
import com.datalift.model.data.PostResource
import com.datalift.model.data.UserData
import com.datalift.model.data.Workout
import com.datalift.model.data.WorkoutResource
import com.datalift.ui.PreviewParameterData.posts
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class PostPreviewParameterProvider : PreviewParameterProvider<List<Post>> {

    override val values: Sequence<List<Post>> = sequenceOf(posts)
}

object PreviewParameterData {
    private val userData: UserData = UserData(
        userID = "3",
        userName = "Sean Cotter",
        userProfileUrl = null
    )

    val posts = listOf(
        Post(
           postResource =  PostResource(
                postId = "1",
                posterId = "1",
                title = "Benching with the boys",
                content = "I benched a few with the boys",
                time = LocalDateTime(
                    year = 2025,
                    monthNumber = 5,
                    dayOfMonth = 4,
                    hour = 12,
                    minute = 0,
                    second = 0,
                ).toInstant(TimeZone.UTC),
               usersLiked = listOf("2","3")
           ),
            userData = userData
        ),
        Post(
            postResource = PostResource(
                postId = "2",
                posterId = "1",
                title = "Squatting Solo Dolo",
                content = "The boys were busy so I did squats by myself",
                time = Instant.parse("2025-05-14T00:00:00.000Z"),
                usersLiked = listOf("2")
            ),
            userData = userData
        ),
        Post(
            postResource = PostResource(
                postId = "3",
                posterId = "2",
                title = "Cardio with me, myself and I",
                content = "I love cardio, if you didn't know",
                time = Instant.parse("2025-05-13T00:00:00.000Z"),
                usersLiked = listOf("1")
            ),
            userData = userData
        ),
    )

    val workouts = listOf(
        Workout(
           workoutResource =  WorkoutResource(
               workoutId = "1",
               workoutName = "Chest and Push",
               date = Instant.parse ("2025-05-14T00:00:00.000Z"),
               muscleGroups = listOf(MuscleGroup.Chest.displayName, MuscleGroup.Push.displayName),
               exercises = listOf(
                   Exercise(
                       name = "Bench Press",
                       bodyPart = "Chest",
                       sets = listOf(
                           ExerciseSet(
                               reps = 10,
                               weight = 200.0
                           ),
                           ExerciseSet(
                               reps = 10,
                               weight = 200.0
                           ),
                           ExerciseSet(
                               reps = 10,
                               weight = 200.0
                           )
                       )
                   ),
                   Exercise(
                       name = "Incline Bench Press",
                       bodyPart = "Chest",
                       sets = listOf(
                           ExerciseSet(
                               reps = 10,
                               weight = 200.0
                           ),
                           ExerciseSet(
                               reps = 10,
                               weight = 200.0
                           ),
                           ExerciseSet(
                               reps = 10,
                               weight = 200.0
                           ),
                       )
                   )
               )
           )
        ),
        Workout(
            workoutResource = WorkoutResource(
                workoutId = "2",
                workoutName = "Pull and Legs",
                date = Instant.parse("2025-05-13T00:00:00.000Z"),
                muscleGroups = listOf(MuscleGroup.Pull.displayName, MuscleGroup.Legs.displayName),
                exercises = listOf(
                    Exercise(
                        name = "Pull Ups",
                        bodyPart = "Pull",
                        sets = listOf(
                            ExerciseSet(
                                reps = 10,
                                weight = 0.0
                            ),
                            ExerciseSet(
                                reps = 10,
                                weight = 0.0
                            ),
                            ExerciseSet(
                                reps = 10,
                                weight = 0.0
                            )
                        )
                    ),
                    Exercise(
                        name = "Leg Press",
                        bodyPart = "Legs",
                        sets = listOf(
                            ExerciseSet(
                                reps = 10,
                                weight = 100.0
                            ),
                            ExerciseSet(
                                reps = 10,
                                weight = 100.0
                            ),
                            ExerciseSet(
                                reps = 10,
                                weight = 100.0
                            )
                        )
                    ),
                    Exercise(
                        name = "Leg Extensions",
                        bodyPart = "Legs",
                        sets = listOf(
                            ExerciseSet(
                                reps = 10,
                                weight = 100.0
                            ),
                            ExerciseSet(
                                reps = 10,
                                weight = 100.0
                            ),
                            ExerciseSet(
                                reps = 10,
                                weight = 100.0
                            )
                        )
                    )
                )
            )
        ),
        Workout(
            workoutResource = WorkoutResource(
                workoutId = "3",
                workoutName = "Cardio",
                date = Instant.parse("2025-05-12T00:00:00.000Z"),
                muscleGroups = listOf(MuscleGroup.Cardio.displayName),
                exercises = listOf(
                    Exercise(
                        name = "Running",
                        bodyPart = "Cardio",
                        sets = listOf(
                            ExerciseSet(
                                reps = 10,
                                weight = 0.0
                            ),
                            ExerciseSet(
                                reps = 10,
                                weight = 0.0
                            ),
                            ExerciseSet(
                                reps = 10,
                                weight = 0.0
                            )
                        )
                    )
                )
            )
        )
    )

    val exerciseResources = listOf(
        ExerciseResource(
            title = "Push-ups"
        ),
        ExerciseResource(
            title = "Pull-ups"
        ),
        ExerciseResource(
            title = "Squats"
        ),
        ExerciseResource(
            title = "Bench Press"
        ),
        ExerciseResource(
            title = "Incline Bench Press"
        )
    )
}