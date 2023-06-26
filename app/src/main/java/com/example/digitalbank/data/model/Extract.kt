package com.example.digitalbank.data.model

import com.google.firebase.database.FirebaseDatabase

data class Extract(
    var id: String = "",
    var operation: String = "",
    var date: Long = 0,
    val amount: Float = 0F,
    var type: String = ""
) {
    init {
        this.id = FirebaseDatabase.getInstance().reference.push().key ?: ""
    }
}
