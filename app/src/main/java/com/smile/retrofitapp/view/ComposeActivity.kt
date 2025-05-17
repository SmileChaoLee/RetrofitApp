package com.smile.retrofitapp.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.view.ui.theme.RetrofitAppTheme
import com.smile.retrofitapp.viewmodels.LanguagesVewModel

private const val TAG = "ComposeActivity"

class ComposeActivity : ComponentActivity() {

    private val viewModel: LanguagesVewModel by viewModels()
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate.setContent()")
        setContent {
            // Loading data
            /*
            LaunchedEffect(Unit) {
                Log.d(TAG, "onCreate.LaunchedEffect")
                withContext(Dispatchers.IO) {
                    languages.value = RestApiUtils.getAllLanguages().languages
                    Log.d(TAG, "onCreateLaunchedEffect.languages.size = " +
                            "${languages.value.size}")
                }
            }
            */
            LaunchedEffect(Unit) {
                viewModel.loadData()
            }
            CreateView(viewModel.languages.value)
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
    fun CreateView( languages: ArrayList<Language>) {
        Log.d(TAG, "CreateView")
        val columnVertical = 30.dp
        val buttonWidth = 200.dp
        val buttonHeight = 60.dp
        val columnModifier = Modifier.fillMaxSize().padding(vertical = columnVertical)
        Column(modifier = columnModifier,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                if (counter%2 == 0) {
                    viewModel.setLanguages(getAllLanguages(1))
                } else {
                    viewModel.loadData()
                }
                counter++
                Log.d(TAG, "CreateView.Button.Clicked.counter = $counter")
            }, modifier = Modifier.size(width = buttonWidth, height = buttonHeight))
            { Text(text = "Update languages") }
        }
        val listVertical = columnVertical + buttonHeight + 30.dp
        val listModifier = Modifier.fillMaxSize().padding(vertical = listVertical)
        if (languages.isNotEmpty()) {
            Log.d(TAG, "CreateView.DisplayLanguages")
            DisplayLanguages(languages, listModifier)
        }
    }
    @Composable
    fun DisplayLanguages(languages: ArrayList<Language>, modifier: Modifier) {
        Log.d(TAG, "DisplayLanguages.languages.size = ${languages.size}")
        LazyColumn(modifier = modifier
            .background(color = androidx.compose.ui.graphics.Color.LightGray)) {
            items(languages) { language ->
                val textModifier = Modifier.padding(start = 2.dp, end = 0.dp)
                val textHeight = 25.dp
                Row (modifier = Modifier.fillMaxSize()) {
                    language.id?.toString()?.let {
                        Text(text = it.padStart(3, ' '),
                            modifier = textModifier.size(width = 40.dp, height = textHeight),
                            fontFamily = FontFamily.Monospace,
                            color = androidx.compose.ui.graphics.Color.Red)
                    }
                    Text(
                        text = language.langNo ?: "",
                        modifier = textModifier.size(width = 40.dp, height = textHeight),
                        fontFamily = FontFamily.Monospace,
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                    Text(
                        text = language.langNa ?: "",
                        modifier = textModifier.size(width = 100.dp, height = textHeight),
                        fontFamily = FontFamily.Monospace,
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                    Text(
                        text = language.langEn ?: "",
                        modifier = textModifier.size(width = 200.dp, height = textHeight),
                        fontFamily = FontFamily.Monospace,
                        color = androidx.compose.ui.graphics.Color.Red
                    )
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