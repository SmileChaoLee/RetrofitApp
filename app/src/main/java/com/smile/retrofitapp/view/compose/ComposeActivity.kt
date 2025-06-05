package com.smile.retrofitapp.view.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smile.retrofitapp.view.compose.ui.theme.RetrofitAppTheme
import com.smile.retrofitapp.view.compose.viewmodels.MainComposeVewModel

private const val TAG = "ComposeActivity"

class ComposeActivity : ComponentActivity() {

    private val viewModel: MainComposeVewModel by viewModels()

    private var defaultFontSize = 20.sp
    private val textFontSize = (defaultFontSize.value * 1f).sp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate.setContent()")
        setContent {
            // Screen View
            Column(modifier = Modifier.fillMaxSize()
                .background(color = Color.Blue),
                horizontalAlignment = Alignment.CenterHorizontally
                , verticalArrangement = Arrangement.Center) {

                var intent by rememberSaveable { mutableStateOf(UserIntents.Languages) }
                viewModel.userIntent(intent)
                // will re-composite the following UI when intent changes

                // begin setting button
                Log.d(TAG, "Column.Button")
                Button(
                    modifier = Modifier.weight(1.0f),
                    onClick = {
                        intent = when(intent) {
                            UserIntents.Languages -> {
                                viewModel.setLanguages(emptyList())
                                UserIntents.GenerateLanguages
                            }
                            UserIntents.GenerateLanguages -> {
                                viewModel.setLanguages(emptyList())
                                UserIntents.Comments
                            }
                            UserIntents.Comments -> {
                                viewModel.setComments(emptyList())
                                UserIntents.Languages
                            }
                        }
                        Log.d(TAG, "Column.Button.Clicked.intent = $intent")
                              },
                    colors = ButtonColors(containerColor = Color.Cyan,
                    disabledContainerColor = Color.DarkGray,
                    contentColor = Color.Red,
                    disabledContentColor = Color.LightGray)
                ) { Text(text = "Update Data",
                    fontSize = textFontSize) }
                // end of button setting
                // HorizontalDivider(color = Color.Blue,
                //     modifier = Modifier.weight(1.0f).fillMaxWidth().size(5.dp))

                when(intent) {
                    UserIntents.Languages -> DisplayLanguages(Modifier.weight(9.0f))
                    UserIntents.GenerateLanguages -> DisplayLanguages(Modifier.weight(9.0f))
                    UserIntents.Comments -> DisplayComments(Modifier.weight(9.0f))
                }
            }
            //
        }
    }

    @Composable
    fun DisplayLanguages(modifier: Modifier) {
        Log.d(TAG, "DisplayLanguages")
        Column(modifier = modifier.fillMaxHeight().fillMaxWidth()
            .background(color = Color(0xff90e5c4))) {
            Text(text = "Languages List", fontSize = textFontSize, color = Color.Blue)
            HorizontalDivider(color = Color.Black, thickness = 3.dp,
                modifier = Modifier.fillMaxWidth())
            Log.d(TAG, "DisplayLanguages.LazyColumn")

            // when state changes for MutableState
            // val languages = viewModel.languages.value
            // when state changes for MutableStateFlow
            val languages by viewModel.languages.collectAsState(listOf())
            Log.d(TAG, "DisplayLanguages.languages.size = " +
                    "${languages.size}")
            if (languages.isEmpty()) return

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
                }
            }
        }
    }

    @Composable
    fun DisplayComments(modifier: Modifier) {
        Log.d(TAG, "DisplayComments")
        Column(modifier = modifier.fillMaxHeight().fillMaxWidth()
            .background(color = Color(0xff90e5c4))) {
            Text(text = "Comments List", fontSize = textFontSize, color = Color.Blue)
            HorizontalDivider(color = Color.Black, thickness = 3.dp,
                modifier = Modifier.fillMaxWidth())

            Log.d(TAG, "DisplayComments.LazyColumn")
            // when state changes for MutableState
            // val languages = viewModel.languages.value
            // when state changes for MutableStateFlow
            val comments by viewModel.comments.collectAsState(listOf())
            Log.d(TAG, "DisplayComments.comments.size = " +
                    "${comments.size}")
            if (comments.isEmpty()) return

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(comments) { comment->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        comment.postId?.toString()?.let {
                            Text(text = it.padStart(3, ' '),
                                modifier = Modifier.weight(2f),
                                fontFamily = FontFamily.Monospace, fontSize = textFontSize,
                                color = Color.Red)
                        }
                        comment.id?.toString()?.let {
                            Text(text = it.padStart(3, ' '),
                                modifier = Modifier.weight(2f),
                                fontFamily = FontFamily.Monospace, fontSize = textFontSize,
                                color = Color.Red)
                        }
                        Text(
                            text = comment.name ?: "",
                            modifier = Modifier.weight(1f),
                            fontFamily = FontFamily.Monospace, fontSize = textFontSize,
                            color = Color.Red
                        )
                        Text(
                            text = comment.email ?: "",
                            Modifier.weight(4f),
                            fontFamily = FontFamily.Monospace, fontSize = textFontSize,
                            color = Color.Red
                        )
                        Text(
                            text = comment.body ?: "",
                            Modifier.weight(6f),
                            fontFamily = FontFamily.Monospace,fontSize = textFontSize,
                            color = Color.Red
                        )
                    }
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