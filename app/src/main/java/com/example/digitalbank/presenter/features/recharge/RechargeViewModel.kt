package com.example.digitalbank.presenter.features.recharge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.digitalbank.data.model.Recharge
import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.domain.recharge.GetRechargeUseCase
import com.example.digitalbank.domain.recharge.SaveRechargeUseCase
import com.example.digitalbank.domain.transaction.SaveTransactionUseCase
import com.example.digitalbank.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RechargeViewModel @Inject constructor(
    private val saveRechargeUseCase: SaveRechargeUseCase,
    private val getRechargeUseCase: GetRechargeUseCase,
    private val saveTransactionUseCase: SaveTransactionUseCase
) : ViewModel() {

    fun saveRecharge(recharge: Recharge) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val result = saveRechargeUseCase(recharge)

            emit(StateView.Sucess(result))
        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }

    fun getRecharge(id: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val recharge = getRechargeUseCase(id)

            emit(StateView.Sucess(recharge))
        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }

    fun saveTransaction(transaction: Transaction) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            saveTransactionUseCase(transaction)

            emit(StateView.Sucess(Unit))
        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}