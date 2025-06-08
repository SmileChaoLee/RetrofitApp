package com.smile.retrofitapp.view.compose.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smile.retrofitapp.models.Constants
import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.retrofit.RestApiSync
import com.smile.retrofitapp.view.compose.uiLayer.CommentViewState
import com.smile.retrofitapp.view.compose.uiLayer.LanguageViewState
import com.smile.retrofitapp.view.compose.uiLayer.UserIntents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainComposeVewModel: ViewModel() {
    companion object {
        private const val TAG = "MainComposeVewModel"
    }

    // View state
    private val _languageState = MutableStateFlow(LanguageViewState())
    val languageState: StateFlow<LanguageViewState> = _languageState
    private fun setLanguageState(state: LanguageViewState) {
        _languageState.value = state
    }

    private val _commentState = MutableStateFlow(CommentViewState())
    val commentState: StateFlow<CommentViewState> = _commentState
    private fun setCommentState(state: CommentViewState) {
        _commentState.value = state
    }
    //

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
            val languages = RestApiSync.getAllLanguages(Constants.CHAO_URL).languages
            Log.d(TAG, "loadLanguages.languages.size = ${languages.size}")
            _languageState.value = LanguageViewState(
                languages = languages, sizeOfList = languages.size)
        }
    }

    private fun generateLanguages() {
        Log.d(TAG, "generateLanguages")
        val languages = ArrayList<Language>().apply {
            for (i in 1..100) {
                val ch = (i+65).toChar().toString()
                add(Language(i, "$i", ch, ch))
            }
        }
        _languageState.value = LanguageViewState(
            languages = languages, sizeOfList = languages.size)
    }

    private fun loadComments() {
        Log.d(TAG, "loadComments")
        viewModelScope.launch(Dispatchers.IO) {
            // val comments = HttpURLConnection.getComments()
            val comments = RestApiSync.getComments(Constants.COMMENTS_URL)
            Log.d(TAG, "loadComments.comments.size = ${comments.size}")
            _commentState.value = CommentViewState(
                comments = comments, sizeOfList = comments.size)
        }
    }
}