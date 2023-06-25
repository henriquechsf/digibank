package com.example.digitalbank.data.model

import com.google.firebase.database.FirebaseDatabase

data class Wallet(
    var id: String = "",
    val userId: String,
    var balance: Float = 0F
) {
    init {
        this.id = FirebaseDatabase.getInstance().reference.push().key ?: ""
    }
}
