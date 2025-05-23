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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.view.ui.theme.RetrofitAppTheme
import com.smile.retrofitapp.viewmodels.LanguagesVewModel

private const val TAG = "ComposeActivity"

class ComposeActivity : ComponentActivity() {

    private val viewModel: LanguagesVewModel by viewModels()
    private var counter = 0

    private var defaultFontSize = 20.sp

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
            DisplayLanguages(viewModel.languages.value)
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
    fun DisplayLanguages(languages: List<Language>) {
        Log.d(TAG, "DisplayLanguages.languages.size = ${languages.size}")
        val textFontSize = (defaultFontSize.value * 1f).sp
        Column(modifier = Modifier.fillMaxSize()
            .background(color = Color(0xff90e5c4))) {
            Text(text = "Languages List", fontSize = textFontSize, color = Color.Blue)
            HorizontalDivider(color = Color.Black,
                modifier = Modifier.fillMaxWidth().size(10.dp))
            Log.d(TAG, "DisplayLanguages.LazyColumn")
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(languages) { language->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        language.id?.toString()?.let {
                            Text(text = it.padStart(3, ' '),
                                modifier = Modifier.weight(2f),
                                fontFamily = FontFamily.Monospace, fontSize = textFontSize,
                                color = Color.Red)
                        }
                        Text(
                            text = language.langNo ?: "",
                            modifier = Modifier.weight(1f),
                            fontFamily = FontFamily.Monospace, fontSize = textFontSize,
                            color = Color.Red
                        )
                        Text(
                            text = language.langNa ?: "",
                            Modifier.weight(4f),
                            fontFamily = FontFamily.Monospace, fontSize = textFontSize,
                            color = Color.Red
                        )
                        Text(
                            text = language.langEn ?: "",
                            Modifier.weight(6f),
                            fontFamily = FontFamily.Monospace,fontSize = textFontSize,
                            color = Color.Red
                        )
                    }
                    HorizontalDivider(color = Color.Blue,
                        modifier = Modifier.fillMaxWidth().size(5.dp))
                }
            }
            Log.d(TAG, "DisplayLanguages.Column.Button")
            Column(modifier = Modifier.fillMaxWidth()
                .background(color = Color.Blue),
                horizontalAlignment = Alignment.CenterHorizontally
                , verticalArrangement = Arrangement.Center) {
                Button(onClick = {
                    if (counter % 2 == 0) {
                        viewModel.setLanguages(getAllLanguages(1))
                    } else {
                        viewModel.loadData()
                    }
                    counter++
                    Log.d(TAG, "DisplayLanguages.Button.Clicked.counter = $counter")
                }, colors = ButtonColors(containerColor = Color.Cyan,
                    disabledContainerColor = Color.DarkGray,
                    contentColor = Color.Red,
                    disabledContentColor = Color.LightGray)
                ) { Text(text = "Update languages",
                    fontSize = textFontSize) }
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