package com.example.digitalbank.presenter.auth.register

import androidx.lifecycle.liveData
import com.example.digitalbank.data.model.User
import com.example.digitalbank.domain.auth.RegisterUseCase
import com.example.digitalbank.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) {

    fun register(user: User) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val registeredUser = registerUseCase.invoke(user)

            emit(StateView.Sucess(registeredUser))
        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}