package com.smile.retrofitapp.view.compose.mvi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.smile.retrofitapp.view.compose.mvi.uiLayer.UserIntents
import com.smile.retrofitapp.view.compose.ui.theme.RetrofitAppTheme
import com.smile.retrofitapp.view.compose.mvi.viewmodels.MainComposeVewModel

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
            Log.d(TAG, "onCreate.setContent().Column")
            Column(modifier = Modifier.fillMaxSize()
                .background(color = Color.Blue),
                horizontalAlignment = Alignment.CenterHorizontally
                , verticalArrangement = Arrangement.Center) {

                Log.d(TAG, "onCreate.setContent().Column.Box")
                Box(modifier = Modifier.weight(9.0f)) {
                    DisplayLanguages()
                    DisplayComments()
                }

                // begin setting button
                Log.d(TAG, "onCreate.setContent().Column.UpdateButton")
                UpdateButton(modifier = Modifier.weight(1.0f))
            }
            //
        }
    }

    @Composable
    fun UpdateButton(modifier: Modifier = Modifier) {
        Log.d(TAG, "UpdateButton")
        Column(modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
            , verticalArrangement = Arrangement.Center) {

            var uIntent: UserIntents by rememberSaveable { mutableStateOf(UserIntents.Languages) }
            viewModel.handleIntent(uIntent)
            Button(
                onClick = {
                    viewModel.releaseIntent(uIntent)
                    uIntent = when(uIntent) {
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
                    Log.d(TAG, "UpdateButton.Button.Clicked.uIntent = $uIntent")
                },
                colors = ButtonColors(containerColor = Color.Cyan,
                    disabledContainerColor = Color.DarkGray,
                    contentColor = Color.Red,
                    disabledContentColor = Color.LightGray)
            ) { Text(text = "Update Data",
                fontSize = textFontSize) }
        }
    }

    @Composable
    fun DisplayLanguages() {
        Log.d(TAG, "DisplayLanguages")

        // when state changes for MutableState
        // val languages = viewModel.languages.value
        // when state changes for MutableStateFlow
        val languageState by viewModel.languageState.collectAsState()
        Log.d(
            TAG, "DisplayLanguages.languageState.sizeOfList = " +
                "${languageState.sizeOfList}")
        if (languageState.sizeOfList == 0) return

        Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()
            .background(color = Color(0xff90e5c4))) {
            Text(text = languageState.listTitle, fontSize = textFontSize, color = Color.Blue)
            HorizontalDivider(color = Color.Black, thickness = 3.dp,
                modifier = Modifier.fillMaxWidth())
            Log.d(TAG, "DisplayLanguages.LazyColumn")
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(languageState.languages) { language->
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
    fun DisplayComments() {
        Log.d(TAG, "DisplayComments")

        // when state changes for MutableState
        // val languages = viewModel.languages.value
        // when state changes for MutableStateFlow
        val commentState by viewModel.commentState.collectAsState()
        Log.d(
            TAG, "DisplayComments.commentState.sizeOfList = " +
                "${commentState.sizeOfList}")
        if (commentState.sizeOfList == 0) return

        Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()
            .background(color = Color(0xff90e5c4))) {
            Text(text = commentState.listTitle, fontSize = textFontSize, color = Color.Blue)
            HorizontalDivider(color = Color.Black, thickness = 3.dp,
                modifier = Modifier.fillMaxWidth())
            Log.d(TAG, "DisplayComments.LazyColumn")
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(commentState.comments) { comment->
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