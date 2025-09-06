package com.example.chares.screens

import androidx.compose.ui.res.stringResource
import com.example.chares.R
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

data class LicenseInfo(val name: String, val license: String, val url: String? = null)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseScreen(navController: NavController) {
    val licenses = listOf(
        LicenseInfo(
            stringResource(id = R.string.app_name),
            "GNU General Public License v3.0",
            "https://github.com/Samm8g/Chaers/LICENSE"
        ),
        LicenseInfo(
            "AndroidX Libraries",
            "Apache License 2.0",
            "https://developer.android.com/jetpack/androidx/licenses/LICENSE-2.0.txt"
        ),
        LicenseInfo(
            "Kotlin Coroutines",
            "Apache License 2.0",
            "https://github.com/Kotlin/kotlinx.coroutines/blob/master/LICENSE"
        ),
        LicenseInfo(
            "Room",
            "Apache License 2.0",
            "https://developer.android.com/jetpack/androidx/licenses/LICENSE-2.0.txt"
        ),
        LicenseInfo(
            "JUnit",
            "Eclipse Public License 1.0",
            "https://www.eclipse.org/legal/epl-v10.html"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.license_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(id = R.string.license_screen_back_button_description))
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(licenses) { license ->
                val context = LocalContext.current
                ListItem(
                    headlineContent = { Text(license.name) },
                    supportingContent = { Text(license.license) },
                    modifier = Modifier.clickable {
                        license.url?.let {
                            val intent = Intent(Intent.ACTION_VIEW, it.toUri())
                            context.startActivity(intent)
                        }
                    }
                )
            }
        }
    }
}