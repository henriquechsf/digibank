package com.example.digitalbank.domain.profile

import com.example.digitalbank.data.repository.profile.ProfileRepository
import javax.inject.Inject

class SaveImageProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(imageProfile: String): String {
        return profileRepository.saveImageProfile(imageProfile)
    }
}