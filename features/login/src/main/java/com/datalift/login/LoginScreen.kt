package com.datalift.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.datalift.designsystem.components.DevicePreviewWithBackground
import com.datalift.designsystem.theme.DataliftTheme
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import kotlinx.coroutines.launch

@Composable
internal fun LoginScreen(
    navigateToSignup: () -> Unit = {},
    navigateToForgotPassword: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel(),
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.loggedIn) {
        if(uiState.loggedIn){
            navigateToHome()
        }
    }

    LoginScreen(
        uiState = uiState,
        navigateToSignup = navigateToSignup,
        navigateToForgotPassword = navigateToForgotPassword,
        loginWithEmailAndPassword = viewModel::loginEmailAndPassword,
        updateUsername = viewModel::updateUsername,
        updatePassword = viewModel::updatePassword,
        loginWithGoogle = viewModel::loginWithGoogle
    )
}

@Composable
internal fun LoginScreen(
    uiState: LoginUiState,
    navigateToSignup: () -> Unit = {},
    navigateToForgotPassword: () -> Unit = {},
    loginWithEmailAndPassword: () -> Unit = {},
    updateUsername: (String) -> Unit = {},
    updatePassword: (String) -> Unit = {},
    loginWithGoogle: (Credential) -> Unit = {}
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.65f)
        ) {
            val textFieldWidth = remember{ mutableIntStateOf(28) }
            val textFieldWidthDp = with(LocalDensity.current) { textFieldWidth.intValue.toDp() }

            DataliftTitle()
            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = uiState.username,
                onValueChange = updateUsername,
                placeholder = { Text("Username") },
                isError = uiState.hasErrors,
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = uiState.password,
                onValueChange = updatePassword,
                placeholder = { Text("Password") },
                isError = uiState.hasErrors,
                modifier = Modifier.onSizeChanged { size ->
                    textFieldWidth.intValue = size.width
                }
            )
            TextButton(
                onClick = navigateToForgotPassword,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Forgot password?")
            }
            Button(
                onClick = loginWithEmailAndPassword,
                modifier = Modifier.width(textFieldWidthDp)
            ) {
                Text(text = "Sign In")
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = navigateToSignup
            ) {
                Text(text = "Create an account")
            }
            Text(
                text = "Or"
            )
            SignInWithGoogleButton(
                onRequestResult = loginWithGoogle,
                modifier = Modifier.width(textFieldWidthDp)
            )
            Text(
                text = uiState.errorMessage,
            )
        }
    }
}

@Composable
private fun DataliftTitle(){
    Text(
        text = "DATALIFT",
        fontFamily = FontFamily.Serif,
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
private fun SignInWithGoogleButton(
    onRequestResult: (Credential) -> Unit,
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            coroutineScope.launch {
                launchCredentialManagerButtonUI(
                    context = context,
                    onRequestResult = onRequestResult
                )
            }
        },
        modifier = modifier
    ){
        Text(text = "Sign in with Google")
    }

}

private suspend fun launchCredentialManagerButtonUI(
    context: Context,
    onRequestResult: (Credential) -> Unit,
){
    try {
        val signInWithGoogleOptions =
            GetSignInWithGoogleOption.Builder(
                serverClientId = context.getString(R.string.server_client_id)
            ).build()

        // Displays the Sign-in with Google UI
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOptions)
            .build()

        val result = CredentialManager.create(context).getCredential(
            request = request,
            context = context
        )

        onRequestResult(result.credential)

    } catch (e: GetCredentialException){
        Log.d("ERROR", e.message.orEmpty())
    } catch (_: Exception){

    }
}

@DevicePreviewWithBackground
@Composable
private fun LoginScreenPreview(){
    DataliftTheme {
        LoginScreen(
            uiState = LoginUiState()
        )
    }
}