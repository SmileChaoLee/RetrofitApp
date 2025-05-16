package com.smile.retrofitapp.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.models.LanguageList
import com.smile.retrofitapp.retrofit2.RestApiUtils
import com.smile.retrofitapp.view.ui.theme.RetrofitAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "ComposeActivity"

class ComposeActivity : ComponentActivity() {
    private val languages = mutableStateOf(ArrayList<Language>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        CoroutineScope(Dispatchers.IO).launch {
            val list = Utils.getLanguages()
            Log.d(TAG, "onCreate.Utils.getLanguages()")
            list.languages.forEach{language ->
                Log.d(TAG, "onCreate.CoroutineScope.id = ${language.id}")
                Log.d(TAG, "onCreate.CoroutineScope.langNo = ${language.langNo}")
                Log.d(TAG, "onCreate.CoroutineScope.langNa = ${language.langNa}")
                Log.d(TAG, "onCreate.CoroutineScope.langEn = ${language.langEn}")
            }
        }
        */
        Log.d(TAG, "onCreate.setContent()")
        // enableEdgeToEdge()
        setContent {
            TextView()
            Button(onClick = {
                languages.value = getAllLanguages(2)
                Log.d(TAG, "Clicked")
            },
                modifier = Modifier.size(300.dp, 100.dp))
            { Text(text="Update languages") }
        }
    }

    private fun getAllLanguages(start: Int): ArrayList<Language> {
        val languages = ArrayList<Language>().apply {
            for (i in start..100) {
                val ch = (i+65).toChar().toString()
                add(Language(i, "$i", ch, ch))
            }
        }
        return languages
    }

    @Composable
    fun TextView() {
        // val languages = remember { mutableStateOf(ArrayList<Language>()) }
        // val lans = remember { languages }
        LaunchedEffect(Unit) {
            Log.d(TAG, "TextView.LaunchedEffect")
            withContext(Dispatchers.IO) {
                languages.value = RestApiUtils.getAllLanguages().languages
                // languages.value = getAllLanguages(1)
                Log.d(TAG, "TextView.languages.size = ${languages.value.size}")
            }
        }
        if (languages.value.isNotEmpty()) {
            Log.d(TAG, "TextView.DisplayLanguages")
            DisplayLanguages(languages)
        }
    }
    @Composable
    fun DisplayLanguages(languages: MutableState<ArrayList<Language>>) {
        Log.d(TAG, "DisplayLanguages.languages.size = ${languages.value.size}")
        val modifier = Modifier.fillMaxSize().padding(vertical = 200.dp)
        LazyColumn(modifier = modifier) {
            items(languages.value) { language ->
                Log.d(TAG, "DisplayLanguages.LazyColumn.Row")
                val textModifier = Modifier.padding(start = 10.dp, end = 0.dp)
                Row(modifier = Modifier.padding(all = 2.dp)) {
                    Log.d(TAG, "DisplayLanguages.LazyColumn.Row.language.id = " +
                            "${language.id}")
                    Text(text = language.id?.toString() ?: "0", modifier = textModifier)
                    Text(text = language.langNo?.trim() ?: "", modifier = textModifier)
                    Text(text = language.langNa?.trim() ?: "", modifier = textModifier)
                    Text(text = language.langEn?.trim() ?: "", modifier = textModifier)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RetrofitAppTheme {
        Greeting("Android")
    }
}