package com.smile.retrofitapp.domain.usecase

import com.smile.retrofitapp.domain.repository.LanguageRepository

class GetLanguageUseCase(
    private val languageRepository: LanguageRepository) {
    suspend operator fun invoke() = languageRepository.getLanguages()
}