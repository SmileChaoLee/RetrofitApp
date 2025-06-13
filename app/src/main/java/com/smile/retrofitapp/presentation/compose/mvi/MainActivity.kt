package com.smile.retrofitapp.presentation.compose.mvi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smile.retrofitapp.domain.model.Task
import com.smile.retrofitapp.domain.usecase.TaskUseCase
import com.smile.retrofitapp.presentation.compose.mvi.ui.theme.RetrofitAppTheme
import com.smile.retrofitapp.presentation.compose.mvi.uiLayer.UserIntents
import com.smile.retrofitapp.presentation.compose.mvi.viewmodels.TaskViewModel

class MainActivity : ComponentActivity() {

    private val viewModel = TaskViewModel(TaskUseCase())
    private val mFontSize = 20.sp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d(TAG, "onCreate.setContent")
            RetrofitAppTheme {
                val buttonListener = object: ButtonClickListener {
                    override fun buttonOkClick(value: Task) {
                        Log.d(TAG, "onCreate.setContent.buttonOkClick")
                        viewModel.handleIntent(UserIntents.TaskWork(value))
                    }
                    override fun buttonCancelClick(value: Task) {
                    }
                }
                Log.d(TAG, "onCreate.setContent.Column")
                Column(modifier = Modifier.fillMaxSize()) {
                    InputTaskProperty(
                        modifier = Modifier.weight(3.0f),
                                buttonListener, "Task")
                    DisplayTasks(Modifier.weight(7.0f))
                }
            }
        }
    }

    interface ButtonClickListener {
        fun buttonOkClick(value: Task)
        fun buttonCancelClick(value: Task)
    }

    @Composable
    fun DisplayTasks(modifier: Modifier = Modifier) {
        Log.d(TAG, "DisplayTasks")
        Column(modifier = modifier.fillMaxWidth()
            .background(color = Color(0xff90e5c4))) {
            Text(text = "Task List", fontSize = mFontSize, color = Color.Blue)
            HorizontalDivider(color = Color.Black, thickness = 3.dp,
                modifier = Modifier.fillMaxWidth())

            val taskState by viewModel.taskState.collectAsState()
            Log.d(TAG, "DisplayTasks.taskState.size = ${taskState.size}")
            if (taskState.isEmpty()) return

            Log.d(TAG, "DisplayTasks.LazyColumn")
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(taskState) { task->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = task.title,
                            modifier = Modifier.weight(1f),
                            fontFamily = FontFamily.Monospace, fontSize = mFontSize,
                            color = Color.Red)
                        Text(
                            text = task.status,
                            modifier = Modifier.weight(1f),
                            fontFamily = FontFamily.Monospace, fontSize = mFontSize,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun textFieldValue(hintText: String): String {
        var textValue by rememberSaveable { mutableStateOf("") }
        Log.d(TAG, "textFieldValue.textValue = $textValue")
        TextField(value = textValue,
            onValueChange = {
                textValue = it
                Log.d(TAG, "textFieldValue.textValue = $textValue")
            },
            textStyle = LocalTextStyle.current.copy(fontSize = mFontSize),
            placeholder = {
                Text(text = hintText, color = Color.LightGray,
                    fontWeight = FontWeight.Light, fontSize = mFontSize) }
        )
        return textValue
    }

    @Composable
    fun InputTaskProperty(modifier: Modifier = Modifier,
                          buttonListener: ButtonClickListener,
                          dialogTitle: String) {
        var renew by rememberSaveable { mutableStateOf(true) }
        Log.d(TAG, "InputTaskProperty.renew = $renew")
        val okStr = "OK"
        val noStr = "Cancel"
        var isOpen by rememberSaveable { mutableStateOf(true) }
        if (isOpen) {
            val task = Task()
            Column(modifier = modifier.fillMaxWidth().fillMaxHeight()
                .background(Color(0xffffa500)),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Column(modifier = Modifier.weight(3.0f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(text = dialogTitle, color = Color.Blue,
                        fontWeight = FontWeight.Bold, fontSize = mFontSize)
                    Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                    task.title = textFieldValue("Input Title")
                    Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                    task.status = textFieldValue("Input Status")
                }
                Row(modifier = Modifier.weight(1.0f),
                    verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = {
                            isOpen = false
                            buttonListener.buttonCancelClick(task)
                        }, colors = ButtonColors(
                            containerColor = Color.LightGray,
                            disabledContainerColor = Color.LightGray,
                            contentColor = Color.Red,
                            disabledContentColor = Color.Red
                        )
                    ) { Text(text = noStr, fontSize = mFontSize) }
                    Button(modifier = Modifier.padding(start = 30.dp),
                        onClick = {
                            renew = !renew
                            buttonListener.buttonOkClick(task)
                        }, colors = ButtonColors(
                            containerColor = Color.DarkGray,
                            disabledContainerColor = Color.DarkGray,
                            contentColor = Color.Yellow,
                            disabledContentColor = Color.Yellow
                        )
                    )
                    { Text(text = okStr, fontSize = mFontSize) }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}