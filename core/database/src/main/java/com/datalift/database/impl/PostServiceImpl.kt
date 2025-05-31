package com.datalift.database.impl

import android.util.Log
import com.datalift.database.service.AccountService
import com.datalift.database.service.PostService
import com.datalift.model.data.Post
import com.datalift.model.data.PostResource
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
): PostService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFeedForUser(userId: String): Flow<List<PostResource>> {
        auth.user.flatMapLatest { user ->
            val userSnapshot = firestore
                .collection("users")
                .document(user.id)
                .get()
                .await()
            
            val followingField = userSnapshot.get("following")

            if(followingField is List<*> && followingField.all { it is String }){
                @Suppress("UNCHECKED_CAST")
                val following = followingField as List<String>
                val posts: MutableList<PostResource> = mutableListOf()

                following.forEach { userId ->
                    firestore.collection("posts")
                        .whereEqualTo("posterId", userId)
                        .get()
                        .addOnSuccessListener { documents ->
                            for(document in documents){
                                val post = document.toObject<PostResource>()
                                posts.add(post)
                            }
                        }.addOnFailureListener { exception ->
                            Log.e("POST","Error getting documents: ", exception)
                        }
                }
                return@flatMapLatest flowOf(posts.toList())
            } else {
                return@flatMapLatest flowOf(emptyList())
            }
        }
        return flowOf(emptyList())
    }

    override fun getPost(postId: String): Flow<Post> {
        if(postId.isBlank()){
            return flow { throw IllegalArgumentException("Post ID cannot be blank") }
        }
        return callbackFlow {
            firestore.collection("posts")
                .document(postId)
                .get()
                .addOnSuccessListener { document ->
                    val post = document.toObject<Post>()
                    if(post != null){
                        trySend(post)
                    } else {
                        cancel("Failed to parse post data")
                    }
                }.addOnFailureListener {
                    cancel("Failed to get post")
                }
        }
    }

    override suspend fun updatePostLikedStatus(postId: String, isLiked: Boolean) {
        if(postId.isBlank()){
            return
        }
        firestore.collection("posts")
            .document(postId)
            .update("usersLiked",
                if(isLiked) FieldValue.arrayUnion(auth.currentUserId)
                else FieldValue.arrayRemove(auth.currentUserId)
            ).await()
    }

}