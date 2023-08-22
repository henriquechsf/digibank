package com.example.digitalbank.domain.recharge

import com.example.digitalbank.data.model.Recharge
import com.example.digitalbank.data.repository.recharge.RechargeRepository
import javax.inject.Inject

class SaveRechargeUseCase @Inject constructor(
    private val rechargeRepository: RechargeRepository
) {

    suspend operator fun invoke(recharge: Recharge): Recharge {
        return rechargeRepository.saveRecharge(recharge)
    }
}