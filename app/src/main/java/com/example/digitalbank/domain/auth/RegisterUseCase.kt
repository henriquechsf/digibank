package com.example.digitalbank.domain.auth

import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSource

class RegisterUseCase(
    private val authFirebaseDataSource: AuthFirebaseDataSource
) {

    suspend operator fun invoke(name: String, email: String, phone: String, password: String) {
        return authFirebaseDataSource.register(name, email, phone, password)
    }
}