package com.example.chares.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chares.R
import com.example.chares.data.Chore
import com.example.chares.viewmodels.ChoreViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChoreList(chores: List<Chore>, onChoreCheckedChange: (Chore, Boolean) -> Unit, showCompletionDate: Boolean = false, viewModel: ChoreViewModel) {
    LazyColumn {
        items(chores) {
            chore ->
            ChoreItem(chore = chore, onCheckedChange = { isChecked ->
                onChoreCheckedChange(chore, isChecked)
            }, showCompletionDate = showCompletionDate, viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChoreItem(chore: Chore, onCheckedChange: (Boolean) -> Unit, showCompletionDate: Boolean, viewModel: ChoreViewModel) {
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .defaultMinSize(minHeight = 72.dp)
        .combinedClickable(
            onClick = { onCheckedChange(!chore.isCompleted) },
            onLongClick = { showDeleteConfirmationDialog = true }
        )
    )
    {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = chore.title)
                if (showCompletionDate && chore.isCompleted && chore.completedAt != null) {
                    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    Text(
                        text = stringResource(id = R.string.chore_completed_at, sdf.format(Date(chore.completedAt))),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Checkbox(checked = chore.isCompleted, onCheckedChange = onCheckedChange)
        }
    }

    if (showDeleteConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            title = { Text(text = stringResource(id = R.string.chore_delete_chore)) },
            text = { Text(text = stringResource(id = R.string.chore_delete_confirmation, chore.title)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.delete(chore)
                    showDeleteConfirmationDialog = false
                }) {
                    Text(stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmationDialog = false }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun AddChoreDialog(onDismiss: () -> Unit, onChoreAdd: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.chore_add_new_chore)) },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(stringResource(id = R.string.chore_title)) }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        onChoreAdd(text)
                    }
                },
                enabled = text.isNotBlank()
            ) {
                Text(stringResource(id = R.string.add))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}
