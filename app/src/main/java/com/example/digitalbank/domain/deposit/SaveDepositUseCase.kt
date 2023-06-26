package com.example.digitalbank.domain.deposit

import com.example.digitalbank.data.model.Deposit
import com.example.digitalbank.data.repository.deposit.DepositRepository
import javax.inject.Inject

class SaveDepositUseCase @Inject constructor(
    private val depositRepository: DepositRepository
) {

    suspend operator fun invoke(deposit: Deposit): Deposit {
        return depositRepository.saveDeposit(deposit)
    }
}