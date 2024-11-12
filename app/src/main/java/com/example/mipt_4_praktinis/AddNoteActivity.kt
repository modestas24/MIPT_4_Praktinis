package com.example.mipt_4_praktinis

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mipt_4_praktinis.data.NoteEntity
import com.example.mipt_4_praktinis.ui.theme.CustomTheme

class AddNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AddNoteScreen(this)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(context: ComponentActivity) {
    CustomTheme {
        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        Button(
                            shape = MaterialTheme.shapes.small,
                            contentPadding = PaddingValues(8.dp, 0.dp, 16.dp, 0.dp),
                            onClick = {
                                context.finish()
                            }
                        ) {
                            Icon(Icons.Rounded.Close, contentDescription = "Cancel")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Cancel")
                        }
                    },
                    actions = {
                        Button(
                            shape = MaterialTheme.shapes.small,
                            contentPadding = PaddingValues(8.dp, 0.dp, 16.dp, 0.dp),
                            onClick = {
                                if (title.isEmpty() && description.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "One of the fields should be filled!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button
                                }

                                val intent = Intent()
                                intent.putExtra("status", "added")
                                intent.putExtra(
                                    "note",
                                    NoteEntity(
                                        title = title,
                                        description = description
                                    )
                                )

                                context.setResult(RESULT_OK, intent)
                                context.finish()
                            }
                        ) {
                            Icon(Icons.Rounded.Check, contentDescription = "Add note")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Add note")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                OutlinedTextField(
                    title,
                    shape = RoundedCornerShape(8.dp),
                    maxLines = 3,
                    textStyle = TextStyle(
                        fontSize = 32.sp,
                    ),
                    onValueChange = { title = it },
                    label = {
                        Text("Title")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .defaultMinSize(minHeight = 128.dp)
                )
                OutlinedTextField(
                    description,
                    shape = RoundedCornerShape(8.dp),
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                    ),
                    onValueChange = { description = it },
                    label = {
                        Text("Description")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .fillMaxHeight()
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddNoteScreenPreview() {
//    AddNoteScreen()
}