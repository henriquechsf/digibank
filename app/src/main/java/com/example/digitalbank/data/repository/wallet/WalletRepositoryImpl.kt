package com.example.digitalbank.data.repository.wallet

import com.example.digitalbank.data.model.Wallet
import com.google.firebase.database.FirebaseDatabase
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
                .child(wallet.id)
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
}