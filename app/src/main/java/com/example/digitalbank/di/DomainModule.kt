package com.example.digitalbank.di

import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSource
import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSourceImpl
import com.example.digitalbank.data.repository.deposit.DepositRepository
import com.example.digitalbank.data.repository.deposit.DepositRepositoryImpl
import com.example.digitalbank.data.repository.profile.ProfileRepository
import com.example.digitalbank.data.repository.profile.ProfileRepositoryImpl
import com.example.digitalbank.data.repository.recharge.RechargeRepository
import com.example.digitalbank.data.repository.recharge.RechargeRepositoryImpl
import com.example.digitalbank.data.repository.transaction.TransactionRepository
import com.example.digitalbank.data.repository.transaction.TransactionRepositoryImpl
import com.example.digitalbank.data.repository.wallet.WalletRepository
import com.example.digitalbank.data.repository.wallet.WalletRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindsAuthRepository(
        authFirebaseDataSourceImpl: AuthFirebaseDataSourceImpl
    ): AuthFirebaseDataSource

    @Binds
    fun bindsProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    fun bindsWalletRepository(
        walletRepositoryImpl: WalletRepositoryImpl
    ): WalletRepository

    @Binds
    fun bindsDepositRepository(
        depositRepositoryImpl: DepositRepositoryImpl
    ): DepositRepository

    @Binds
    fun bindsTransactionRepository(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    fun bindsRechargeRepository(
        rechargeRepositoryImpl: RechargeRepositoryImpl
    ): RechargeRepository
}