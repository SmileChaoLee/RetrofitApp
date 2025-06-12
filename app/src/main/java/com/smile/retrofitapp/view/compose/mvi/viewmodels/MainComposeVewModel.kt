package com.smile.retrofitapp.view.compose.mvi.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smile.retrofitapp.data.repositoryImpl.CommentRepositoryImpl
import com.smile.retrofitapp.data.repositoryImpl.GenLanguageRepositoryImpl
import com.smile.retrofitapp.data.repositoryImpl.LanguageRepositoryImpl
import com.smile.retrofitapp.domain.usecase.GetCommentUseCase
import com.smile.retrofitapp.domain.usecase.GetGenLanguageUseCase
import com.smile.retrofitapp.domain.usecase.GetLanguageUseCase
import com.smile.retrofitapp.view.compose.mvi.uiLayer.CommentViewState
import com.smile.retrofitapp.view.compose.mvi.uiLayer.LanguageViewState
import com.smile.retrofitapp.view.compose.mvi.uiLayer.UserIntents
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
    //

    private val _intent = MutableStateFlow(UserIntents.Languages)
    val intent: StateFlow<UserIntents> = _intent.asStateFlow()

    fun updateIntent(uIntent: UserIntents) {
        _intent.value = when(uIntent) {
            UserIntents.Languages -> {
                UserIntents.GenerateLanguages
            }
            UserIntents.GenerateLanguages -> {
                UserIntents.Comments
            }
            UserIntents.Comments -> {
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
        }
    }

    private fun loadLanguages() {
        Log.d(TAG, "loadLanguages")
        viewModelScope.launch(Dispatchers.IO) {
            // getLanguageUseCase can be injected
            val getLanguageUseCase = GetLanguageUseCase(LanguageRepositoryImpl())
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
            // getGenLanguageUseCase can be injected
            val getGenLanguageUseCase = GetGenLanguageUseCase(GenLanguageRepositoryImpl())
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
            // getCommentUseCase can be injected
            val getCommentUseCase = GetCommentUseCase(CommentRepositoryImpl())
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