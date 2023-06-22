package com.example.digitalbank.domain.auth

import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSource

class ForgotUseCase(
    private val authFirebaseDataSource: AuthFirebaseDataSource
) {

    suspend operator fun invoke(email: String) {
        return authFirebaseDataSource.forgot(email)
    }
}