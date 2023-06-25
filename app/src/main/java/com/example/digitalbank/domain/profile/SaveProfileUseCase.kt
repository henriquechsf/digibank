package com.example.digitalbank.domain.profile

import com.example.digitalbank.data.model.User
import com.example.digitalbank.data.repository.profile.ProfileRepository
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
){

    suspend operator fun invoke(user: User) {
        return profileRepository.saveProfile(user)
    }
}