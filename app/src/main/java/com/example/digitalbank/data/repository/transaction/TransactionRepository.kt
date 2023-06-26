package com.example.digitalbank.data.repository.transaction

import com.example.digitalbank.data.model.Transaction

interface TransactionRepository {

    suspend fun saveTransaction(transaction: Transaction)

    suspend fun getTransactions(): List<Transaction>
}