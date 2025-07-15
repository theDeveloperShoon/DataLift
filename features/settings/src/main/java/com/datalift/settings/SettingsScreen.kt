package com.datalift.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.datalift.designsystem.icon.DataliftIcons
import com.datalift.designsystem.theme.DataliftTheme

@Composable
internal fun SettingsScreen(
    navUp: () -> Unit,
    navigateToLogin: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
){
    SettingsScreen(
        navUp = navUp,
        signOutUser = {
            settingsViewModel.signOut()
            navigateToLogin()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    navUp: () -> Unit,
    signOutUser: () -> Unit
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton (onClick = { navUp() }) {
                        Icon(
                            DataliftIcons.NavigateUp,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, top = 4.dp, end = 4.dp, bottom = 4.dp)
                    .clickable(onClick = signOutUser)
            ) {
                Text(text = "Log out")
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview(){
    DataliftTheme {
        SettingsScreen(
            navUp = {},
            signOutUser = {}
        )
    }
}