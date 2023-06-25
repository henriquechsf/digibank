package com.example.digitalbank.data.repository.wallet

import com.example.digitalbank.data.model.Wallet
import com.example.digitalbank.utils.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class WalletRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase
) : WalletRepository {

    private val walletRef = database.reference
        .child("wallet")

    override suspend fun initWallet(wallet: Wallet) {
        return suspendCoroutine { continuation ->
            walletRef
                .child(FirebaseHelper.getUserId())
                .setValue(wallet).addOnCompleteListener { task ->
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

    override suspend fun getWallet(): Wallet {
        return suspendCoroutine { continuation ->
            walletRef
                .child(FirebaseHelper.getUserId())

            walletRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val wallet = snapshot.getValue(Wallet::class.java)

                    wallet?.let { continuation.resumeWith(Result.success(it)) }
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWith(Result.failure(error.toException()))
                }

            })
        }
    }
}