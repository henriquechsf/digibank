package com.example.digitalbank.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.digitalbank.domain.profile.GetProfileUseCase
import com.example.digitalbank.domain.transaction.GetTransactionsUseCase
import com.example.digitalbank.domain.wallet.GetWalletUseCase
import com.example.digitalbank.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWalletUseCase: GetWalletUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {

    fun getWallet() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val wallet = getWalletUseCase()

            emit(StateView.Sucess(wallet))
        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }

    fun getTransactions() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val transactions = getTransactionsUseCase()

            emit(StateView.Sucess(transactions))
        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }

    fun getProfile() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val user = getProfileUseCase()

            emit(StateView.Sucess(user))
        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}