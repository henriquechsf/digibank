package com.example.digitalbank.domain.wallet

import com.example.digitalbank.data.model.Wallet
import com.example.digitalbank.data.repository.wallet.WalletRepository
import javax.inject.Inject

class GetWalletUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) {

    suspend operator fun invoke(): Wallet {
        return walletRepository.getWallet()
    }
}