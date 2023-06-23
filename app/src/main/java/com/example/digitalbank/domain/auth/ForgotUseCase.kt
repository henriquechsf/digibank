package com.example.digitalbank.domain.auth

import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSource
import javax.inject.Inject

class ForgotUseCase @Inject constructor(
    private val authFirebaseDataSource: AuthFirebaseDataSource
) {

    suspend operator fun invoke(email: String) {
        return authFirebaseDataSource.forgot(email)
    }
}