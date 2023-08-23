package com.example.digitalbank.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun providesFirebaseDatabase() = FirebaseDatabase.getInstance()

    @Provides
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun providesFirebaseStorage() = FirebaseStorage.getInstance()
}