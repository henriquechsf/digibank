package com.example.digitalbank.domain.transaction

import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.data.repository.transaction.TransactionRepository
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(): List<Transaction> {
        return transactionRepository.getTransactions()
    }
}