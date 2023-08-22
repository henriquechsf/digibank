package com.example.digitalbank.data.repository.profile

import com.example.digitalbank.data.model.User
import com.example.digitalbank.utils.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class ProfileRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase
) : ProfileRepository {

    private val profileRef = database.reference
        .child("profile")

    override suspend fun saveProfile(user: User) {
        return suspendCoroutine { continuation ->
            profileRef
                .child(FirebaseHelper.getUserId())
                .setValue(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resumeWith(Result.success(Unit))
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }

    override suspend fun getProfile(): User {
        return suspendCoroutine { continuation ->
            profileRef
                .child(FirebaseHelper.getUserId())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        user?.let {
                            continuation.resumeWith(Result.success(it))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resumeWith(Result.failure(error.toException()))
                    }

                })
        }
    }

    override suspend fun getProfileList(): List<User> {
        return suspendCoroutine { continuation ->
            profileRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userList = mutableListOf<User>()

                    snapshot.children.forEach { dataSnapshot ->
                        val user = dataSnapshot.getValue(User::class.java)
                        user?.let { userList.add(it) }
                    }

                    continuation.resumeWith(
                        Result.success(
                            userList.apply {
                                removeAll { it.id == FirebaseHelper.getUserId() }
                            }
                        )
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWith(Result.failure(error.toException()))
                }

            })
        }
    }
}