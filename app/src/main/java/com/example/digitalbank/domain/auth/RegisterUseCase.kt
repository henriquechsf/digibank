package com.example.digitalbank.domain.auth

import com.example.digitalbank.data.model.User
import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSource
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authFirebaseDataSource: AuthFirebaseDataSource
) {

    suspend operator fun invoke(user: User): User {
        return authFirebaseDataSource.register(user)
    }
}