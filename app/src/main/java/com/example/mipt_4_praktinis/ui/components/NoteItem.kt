package com.example.mipt_4_praktinis.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import com.example.mipt_4_praktinis.data.NoteEntity

@Composable
fun NoteCard(note: NoteEntity, modifier: Modifier = Modifier) {
    ListItem(
        modifier = modifier.clip(MaterialTheme.shapes.small),
        leadingContent = {
            Icon(
                Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                "Swap to remove",
                modifier = Modifier.padding(4.dp, 16.dp)
            )
        },
        headlineContent = {
            Text(
                note.title.ifEmpty { "'Empty title'" },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
        },
        supportingContent = {
            Text(
                note.description.ifEmpty { "'Empty description'" },
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, end = 8.dp)
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Color(145, 48, 48)
        else -> Color.Transparent
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "delete",
            tint = Color.White
        )
        Spacer(modifier = Modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(
    note: NoteEntity,
    onClick: (NoteEntity) -> Unit,
    onRemove: (NoteEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentItem by rememberUpdatedState(note)
    val dismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = { it * 0.75f },
        confirmValueChange = {
            if (SwipeToDismissBoxValue.Settled == it) {
                return@rememberSwipeToDismissBoxState false
            }
            if (SwipeToDismissBoxValue.StartToEnd == it) {
                onRemove(currentItem)
                Toast.makeText(context, "Note has been removed!", Toast.LENGTH_SHORT).show()
            }

            return@rememberSwipeToDismissBoxState true
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            DismissBackground(dismissState)
        },
        enableDismissFromEndToStart = false
    ) {
        Surface(
            onClick = { onClick(currentItem) }
        ) {
            NoteCard(note, modifier = modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteItemPreview() {
    NoteItem(
        NoteEntity(
            0,
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
        ),
        {},
        {}
    )
}