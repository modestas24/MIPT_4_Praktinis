package com.example.mipt_4_praktinis

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mipt_4_praktinis.data.NoteEntity
import com.example.mipt_4_praktinis.state.NoteViewModel
import com.example.mipt_4_praktinis.ui.components.NoteItem
import com.example.mipt_4_praktinis.ui.theme.CustomTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val noteViewModel = NoteViewModel()
            NotesScreen(noteViewModel, this)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(viewModel: NoteViewModel, context: Context) {
    // Intent is initialized on button click.
    lateinit var intent: Intent
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        val data = activityResult.data
            ?: return@rememberLauncherForActivityResult
        val note = data.extras?.getParcelable("note", NoteEntity::class.java)
            ?: return@rememberLauncherForActivityResult
        val status = data.extras?.getString("status")
            ?: return@rememberLauncherForActivityResult

        if (note.title.isEmpty() && note.description.isEmpty()) return@rememberLauncherForActivityResult

        println(note.toString())

        when (status) {
            "added" -> viewModel.add(note)
            "updated" -> viewModel.update(note)
            else -> return@rememberLauncherForActivityResult
        }

        Toast.makeText(context, "Note has been ${status}!", Toast.LENGTH_SHORT).show()
    }

    CustomTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Your notes",
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    actions = {
                        Button(
                            shape = MaterialTheme.shapes.small,
                            contentPadding = PaddingValues(8.dp, 0.dp, 16.dp, 0.dp),
                            onClick = {
                                intent = Intent(context, AddNoteActivity::class.java)
                                launcher.launch(intent)
                            }
                        ) {
                            Icon(Icons.Rounded.Add, contentDescription = "Add note")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Add note")
                        }
                    }
                )
            }
        ) { innerPadding ->
            val noteList = viewModel.findAll.collectAsState(initial = listOf())
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                itemsIndexed(
                    noteList.value,
                    key = { _, item -> item.hashCode() }
                ) { _, item ->
                    NoteItem(
                        item,
                        {
                            intent = Intent(context, NoteDetailsActivity::class.java)
                            intent.putExtra("note", item)
                            launcher.launch(intent)
                        },
                        {
                            viewModel.remove(item)
                        },
                    )
                }
            }
        }
    }
}