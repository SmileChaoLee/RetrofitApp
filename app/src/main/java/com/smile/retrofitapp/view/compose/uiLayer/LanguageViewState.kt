package com.smile.retrofitapp.view.compose.uiLayer

import com.smile.retrofitapp.models.Language

data class LanguageViewState(
    val listTitle: String = "Languages List",
    val languages: List<Language> = emptyList(),
    val sizeOfList: Int = 0
)