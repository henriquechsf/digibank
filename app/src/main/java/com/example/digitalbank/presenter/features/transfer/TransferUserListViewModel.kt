package com.example.digitalbank.presenter.features.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.digitalbank.domain.profile.GetProfileListUseCase
import com.example.digitalbank.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class TransferUserListViewModel @Inject constructor(
    private val getProfileListUseCase: GetProfileListUseCase
) : ViewModel() {

    fun getProfileList() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val userList = getProfileListUseCase()

            emit(StateView.Sucess(userList))
        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}