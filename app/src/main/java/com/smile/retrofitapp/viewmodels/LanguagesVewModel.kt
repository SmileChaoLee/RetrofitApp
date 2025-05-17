package com.smile.retrofitapp.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.retrofit2.RestApiSync
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LanguagesVewModel: ViewModel() {
    companion object {
        private const val TAG = "LanguagesVewModel"
    }

    private val restApiSync = RestApiSync()
    private val _languages = mutableStateOf(ArrayList<Language>())
    val languages: MutableState<ArrayList<Language>>
        get() = _languages

    fun setLanguages(languages: ArrayList<Language>) {
        _languages.value = languages
    }

    fun loadData() {
        Log.d(TAG, "loadData")
        viewModelScope.launch(Dispatchers.IO) {
            _languages.value = restApiSync.getAllLanguages().languages
            val comments = RestApiSync("https://jsonplaceholder.typicode.com/").getComments()
            Log.d(TAG, "loadData.comments = $comments")
            // val comments = HttpURLConnection.getComments()
            Log.d(TAG, "loadData.comments.size = ${comments.size}")
        }
    }
}