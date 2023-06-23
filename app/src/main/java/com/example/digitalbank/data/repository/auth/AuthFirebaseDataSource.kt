package com.example.digitalbank.data.repository.auth

import com.example.digitalbank.data.model.User

interface AuthFirebaseDataSource {

    suspend fun login(email: String, password: String)

    suspend fun register(user: User): User

    suspend fun forgot(email: String)
}