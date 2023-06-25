package com.example.digitalbank.data.repository.profile

import com.example.digitalbank.data.model.User

interface ProfileRepository {

    suspend fun saveProfile(user: User)
}