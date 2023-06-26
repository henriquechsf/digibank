package com.example.digitalbank.data.repository.deposit

import com.example.digitalbank.data.model.Deposit

interface DepositRepository {

    suspend fun saveDeposit(deposit: Deposit): String
}