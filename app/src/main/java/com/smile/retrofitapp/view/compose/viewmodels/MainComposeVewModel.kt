package com.smile.retrofitapp.view.compose.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smile.retrofitapp.models.Comment
import com.smile.retrofitapp.models.Constants
import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.retrofit.RestApiSync
import com.smile.retrofitapp.view.compose.UserIntents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainComposeVewModel: ViewModel() {
    companion object {
        private const val TAG = "MainComposeVewModel"
    }

    // View state
    private val _languages = MutableStateFlow(listOf<Language>())
    val languages: StateFlow<List<Language>> = _languages
    fun setLanguages(languages: List<Language>) {
        _languages.value = languages
    }

    private val _comments = MutableStateFlow(listOf<Comment>())
    val comments: StateFlow<List<Comment>> = _comments
    fun setComments(comments: List<Comment>) {
        _comments.value = comments
    }
    //

    fun userIntent(intent: UserIntents) {
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

    private fun loadLanguages() {
        Log.d(TAG, "loadLanguages")
        viewModelScope.launch(Dispatchers.IO) {
            _languages.value = RestApiSync.getAllLanguages(Constants.CHAO_URL).languages
            Log.d(TAG, "loadLanguages.languages.size = ${_languages.value.size}")
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
        _languages.value = languages
    }

    private fun loadComments() {
        Log.d(TAG, "loadComments")
        viewModelScope.launch(Dispatchers.IO) {
            // val comments = HttpURLConnection.getComments()
            _comments.value = RestApiSync.getComments(Constants.COMMENTS_URL)
            Log.d(TAG, "loadComments.comments.size = ${_comments.value.size}")
        }
    }
}