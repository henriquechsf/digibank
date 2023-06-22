package com.example.digitalbank.data.repository.auth

import com.google.firebase.database.FirebaseDatabase

class AuthFirebaseDataSourceImpl(
    firebaseDatabase: FirebaseDatabase
) : AuthFirebaseDataSource {

    override suspend fun login(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun register(name: String, email: String, phone: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun forgot(email: String) {
        TODO("Not yet implemented")
    }
}