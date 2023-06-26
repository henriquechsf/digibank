package com.example.digitalbank.data.repository.deposit

import com.example.digitalbank.data.model.Deposit
import com.example.digitalbank.utils.FirebaseHelper
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class DepositRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase
) : DepositRepository {

    private val depositRef = database.reference
        .child("deposit")
        .child(FirebaseHelper.getUserId())

    override suspend fun saveDeposit(deposit: Deposit): String {
        return suspendCoroutine { continuation ->
            depositRef
                .child(deposit.id)
                .setValue(deposit).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resumeWith(Result.success(deposit.id))
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }
}