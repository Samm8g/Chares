package com.example.chares.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chares.viewmodels.ChoreViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.example.chares.R
import com.example.chares.composables.ChoreList

@Composable
fun AllHistoryScreen(navController: NavController, viewModel: ChoreViewModel) {
    val chores by viewModel.allChores.collectAsState(initial = emptyList())

    if (chores.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(id = R.string.no_chares_found))
        }
    } else {
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
}
