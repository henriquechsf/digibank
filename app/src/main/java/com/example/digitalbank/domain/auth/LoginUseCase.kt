package com.example.digitalbank.domain.auth

import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSource
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authFirebaseDataSource: AuthFirebaseDataSource
) {

    suspend operator fun invoke(email: String, password: String) {
        return authFirebaseDataSource.login(email, password)
    }
}