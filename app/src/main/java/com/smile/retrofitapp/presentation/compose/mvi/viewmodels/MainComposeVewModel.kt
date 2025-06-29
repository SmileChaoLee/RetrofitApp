package com.smile.retrofitapp.presentation.compose.mvi.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smile.retrofitapp.data.repositoryImpl.CommentRepositoryImpl
import com.smile.retrofitapp.data.repositoryImpl.GenLanguageRepositoryImpl
import com.smile.retrofitapp.data.repositoryImpl.LanguageRepositoryImpl
import com.smile.retrofitapp.domain.usecase.GetCommentUseCase
import com.smile.retrofitapp.domain.usecase.GetGenLanguageUseCase
import com.smile.retrofitapp.domain.usecase.GetLanguageUseCase
import com.smile.retrofitapp.presentation.compose.mvi.uiLayer.CommentViewState
import com.smile.retrofitapp.presentation.compose.mvi.uiLayer.LanguageViewState
import com.smile.retrofitapp.presentation.compose.mvi.uiLayer.UserIntents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainComposeVewModel: ViewModel() {
    companion object {
        private const val TAG = "MainComposeVewModel"
    }

    // getGenLanguageUseCase can be injected
    private val getGenLanguageUseCase = GetGenLanguageUseCase(GenLanguageRepositoryImpl())
    // getLanguageUseCase can be injected
    private val getLanguageUseCase = GetLanguageUseCase(LanguageRepositoryImpl())
    // getCommentUseCase can be injected
    private val getCommentUseCase = GetCommentUseCase(CommentRepositoryImpl())

    // View state
    private val _languageState = MutableStateFlow(LanguageViewState())
    val languageState: StateFlow<LanguageViewState> = _languageState.asStateFlow()
    private fun setLanguageState(state: LanguageViewState) {
        _languageState.value = state
    }

    private val _commentState = MutableStateFlow(CommentViewState())
    val commentState: StateFlow<CommentViewState> = _commentState.asStateFlow()
    private fun setCommentState(state: CommentViewState) {
        _commentState.value = state
    }

    private val _intent = MutableStateFlow<UserIntents>(UserIntents.Languages)
    val intent: StateFlow<UserIntents> = _intent.asStateFlow()

    fun updateIntent(intent: UserIntents) {
        _intent.value = when(intent) {
            UserIntents.Languages -> {
                UserIntents.GenerateLanguages
            }
            UserIntents.GenerateLanguages -> {
                UserIntents.Comments
            }
            UserIntents.Comments -> {
                UserIntents.Languages
            }
            UserIntents.Tasks -> {
                UserIntents.Languages
            }
            is UserIntents.TaskWork -> {
                UserIntents.Languages
            }
        }
    }

    fun handleIntent(intent: UserIntents) {
        when (intent) {
            UserIntents.Languages -> {
                loadLanguages()
            }
            UserIntents.GenerateLanguages -> {
                generateLanguages()
            }
            UserIntents.Comments -> {
                loadComments()
            }
            UserIntents.Tasks -> {
            }
            is UserIntents.TaskWork -> {
            }
        }
    }

    fun releaseIntent(intent: UserIntents) {
        when (intent) {
            UserIntents.Languages -> {
                setLanguageState(LanguageViewState())
            }
            UserIntents.GenerateLanguages -> {
                setLanguageState(LanguageViewState())
            }
            UserIntents.Comments -> {
                setCommentState(CommentViewState())
            }
            UserIntents.Tasks -> {}
            is UserIntents.TaskWork -> {}
        }
    }

    private fun loadLanguages() {
        Log.d(TAG, "loadLanguages")
        viewModelScope.launch(Dispatchers.IO) {
            val languages = getLanguageUseCase()
            // val languages = RestApiSync.getAllLanguages(Constants.CHAO_URL).languages
            Log.d(TAG, "loadLanguages.languages.size = ${languages.size}")
            _languageState.update {
                it.copy(languages = languages, sizeOfList = languages.size)
            }
            /*
            _languageState.value = LanguageViewState(
                languages = languages, sizeOfList = languages.size)
             */
        }
    }

    private fun generateLanguages() {
        Log.d(TAG, "generateLanguages")
        /*
        val languages = ArrayList<Language>().apply {
            for (i in 1..100) {
                val ch = (i+65).toChar().toString()
                add(Language(i, "$i", ch, ch))
            }
        }
        */
        viewModelScope.launch(Dispatchers.Default) {
            val languages = getGenLanguageUseCase()
            _languageState.update {
                it.copy(languages = languages, sizeOfList = languages.size)
            }
            /*
        _languageState.value = LanguageViewState(
            languages = languages, sizeOfList = languages.size)

         */
        }
    }

    private fun loadComments() {
        Log.d(TAG, "loadComments")
        viewModelScope.launch(Dispatchers.IO) {
            // val comments = RestApiSync.getComments(Constants.COMMENTS_URL)
            val comments = getCommentUseCase()
            Log.d(TAG, "loadComments.comments.size = ${comments.size}")
            _commentState.update {
                it.copy(comments = comments, sizeOfList = comments.size)
            }
            /*
            _commentState.value = CommentViewState(
                comments = comments, sizeOfList = comments.size)
             */
        }
    }
}