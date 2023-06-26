package com.example.digitalbank.data.repository.transaction

import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.utils.FirebaseHelper
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class TransactionRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase
) : TransactionRepository {

    private val transactionRef = database.reference
        .child("transaction")
        .child(FirebaseHelper.getUserId())

    override suspend fun saveTransaction(transaction: Transaction) {
        return suspendCoroutine { continuation ->
            transactionRef
                .child(transaction.id)
                .setValue(transaction).addOnCompleteListener { task ->
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
}