package com.datalift.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.datalift.designsystem.icon.DataliftIcons
import com.datalift.designsystem.theme.DataliftTheme

@Composable
internal fun SignupScreen(
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    signupViewModel: SignupViewModel = hiltViewModel()
){
    val uiState by signupViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.loggedIn) {
        if(uiState.loggedIn){
            navigateToHome()
        }
    }

    SignupScreen(
        uiState = uiState,
        updateEmail = signupViewModel::updateEmail,
        updatePassword = signupViewModel::updatePassword,
        signUp = signupViewModel::signUp,
        navigateUp = navigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignupScreen(
    uiState: SignupUiState,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    signUp: () -> Unit,
    navigateUp: () -> Unit
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sign Up") },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = DataliftIcons.NavigateUp,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(0.65f)
            ) {
                TextField(
                    value = uiState.username,
                    onValueChange = updateEmail,
                    placeholder = { Text("Email") },
                    isError = uiState.hasErrors
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = uiState.password,
                    onValueChange = updatePassword,
                    placeholder = { Text("Password") },
                    isError = uiState.hasErrors
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = signUp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Sign Up")
                }
            }
        }
    }
}

@Preview
@Composable
private fun SignupScreenPreview(){
    DataliftTheme {
        SignupScreen(
            uiState = SignupUiState(),
            updateEmail = {},
            updatePassword = {},
            signUp = {},
            navigateUp = {}
        )
    }
}