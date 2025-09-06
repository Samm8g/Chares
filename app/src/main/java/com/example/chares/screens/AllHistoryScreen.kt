package com.example.chares.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chares.viewmodels.ChoreViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import com.example.chares.composables.ChoreList

@Composable
fun AllHistoryScreen(navController: NavController, viewModel: ChoreViewModel) {
    val chores by viewModel.allChores.collectAsState(initial = emptyList())

    Column(modifier = Modifier.padding(0.dp)) {
        ChoreList(
            chores = chores.sortedByDescending { it.id },
            onChoreCheckedChange = { chore, isChecked ->
                viewModel.update(chore, isChecked)
            },
            showCompletionDate = true,
            viewModel = viewModel
        )
    }
}
