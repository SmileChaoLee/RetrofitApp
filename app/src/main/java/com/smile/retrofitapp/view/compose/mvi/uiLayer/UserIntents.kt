package com.smile.retrofitapp.view.compose.mvi.uiLayer

sealed class UserIntents {
    data object Languages : UserIntents()
    data object GenerateLanguages: UserIntents()
    data object Comments: UserIntents()
}

/*
enum class UserIntents {
    Languages,
    GenerateLanguages,
    Comments,
}
 */