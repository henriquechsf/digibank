package com.example.digitalbank.presenter.features.deposit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.digitalbank.data.model.Deposit
import com.example.digitalbank.domain.deposit.SaveDepositUseCase
import com.example.digitalbank.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DepositViewModel @Inject constructor(
    private val saveDepositUseCase: SaveDepositUseCase
) : ViewModel() {

    fun saveDeposit(deposit: Deposit) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val depositId = saveDepositUseCase(deposit)

            emit(StateView.Sucess(depositId))
        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}