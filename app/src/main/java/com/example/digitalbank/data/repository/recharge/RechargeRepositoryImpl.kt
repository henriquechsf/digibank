package com.example.digitalbank.data.repository.recharge

import com.example.digitalbank.data.model.Recharge
import com.example.digitalbank.utils.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class RechargeRepositoryImpl @Inject constructor(
    database: FirebaseDatabase
) : RechargeRepository {

    private val rechargeRef = database.reference
        .child("recharge")
        .child(FirebaseHelper.getUserId())

    override suspend fun saveRecharge(recharge: Recharge): Recharge {
        return suspendCoroutine { continuation ->
            rechargeRef
                .child(recharge.id)
                .setValue(recharge).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resumeWith(Result.success(recharge))
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }

    override suspend fun getRecharge(id: String): Recharge {
        return suspendCoroutine { continuation ->
            rechargeRef
                .child(id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val recharge = snapshot.getValue(Recharge::class.java)
                        recharge?.let {
                            continuation.resumeWith(Result.success(it))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        error.toException().let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                })
        }
    }
}