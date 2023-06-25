package com.example.digitalbank.di

import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSource
import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSourceImpl
import com.example.digitalbank.data.repository.profile.ProfileRepository
import com.example.digitalbank.data.repository.profile.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindsAuthRepository(
        authFirebaseDataSourceImpl: AuthFirebaseDataSourceImpl
    ): AuthFirebaseDataSource

    @Binds
    fun bindsProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository
}