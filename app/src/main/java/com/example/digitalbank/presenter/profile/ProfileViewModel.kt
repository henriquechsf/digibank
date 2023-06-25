package com.example.digitalbank.presenter.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.digitalbank.data.model.User
import com.example.digitalbank.domain.profile.SaveProfileUseCase
import com.example.digitalbank.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val saveProfileUseCase: SaveProfileUseCase
) : ViewModel() {

    fun saveProfile(user: User) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            saveProfileUseCase(user)

            emit(StateView.Sucess(null))
        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}