package com.example.digitalbank.domain.wallet

import com.example.digitalbank.data.model.Wallet
import com.example.digitalbank.data.repository.wallet.WalletRepository
import javax.inject.Inject

class InitWalletUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) {

    suspend operator fun invoke(wallet: Wallet) {
        return walletRepository.initWallet(wallet)
    }
}