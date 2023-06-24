package com.example.digitalbank.utils

import com.google.firebase.auth.FirebaseAuth

// TODO: Mover implementacao para classe repository
class FirebaseHelper {

    companion object {
        fun isAuthenticated(): Boolean = FirebaseAuth.getInstance().currentUser != null
    }
}