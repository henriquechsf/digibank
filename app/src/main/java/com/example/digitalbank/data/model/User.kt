package com.example.digitalbank.data.model

import com.google.firebase.database.Exclude

data class User(
    val id: String? = "",
    val name: String,
    val email: String,
    val phone: String,
    val password: String
)
