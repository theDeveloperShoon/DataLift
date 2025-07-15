package com.datalift.login

import androidx.credentials.Credential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datalift.common.Result
import com.datalift.data.repository.CredentialsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val credentialsRepository: CredentialsRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private fun checkCanLogin() : Boolean{
        return _uiState.value.username.isNotEmpty()
                && _uiState.value.password.length >= 6
    }

    fun updateUsername(username: String){
        _uiState.update { currentState ->
            currentState.copy(
                username = username,
                canLogin = checkCanLogin(),
                errorMessage = "",
                hasErrors = false
            )
        }
    }

    fun updatePassword(password: String){
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                canLogin = checkCanLogin(),
                errorMessage = "",
                hasErrors = false
            )
        }
    }

    private fun updateError(error: String = ""){
        _uiState.update { currentState ->
            currentState.copy(
                hasErrors = true,
                errorMessage = error,
            )
        }
    }

    private fun loginUser(){
        _uiState.update { currentState ->
            currentState.copy(
                loggedIn = true
            )
        }
    }

    fun loginEmailAndPassword(){
        if(!checkCanLogin()) return

        viewModelScope.launch {
            credentialsRepository.signInWithEmailAndPassword(
                email = _uiState.value.username,
                password = _uiState.value.password
            ).collect { result ->

                when(result){
                    is Result.Error -> {
                        updateError(
                            error = result.exception.message ?: "Unknown Error"
                        )
                    }
                    is Result.Success<*> -> {
                        loginUser()
                    }
                    else -> Unit
                }
            }
        }
    }

    fun loginWithGoogle(credential: Credential){
        viewModelScope.launch {
            credentialsRepository
                .signInWithGoogle(credential)
                .collect { result ->
                    when (result) {
                        is Result.Error -> {
                            updateError(
                                error = result.exception.message ?: "Unknown Error"
                            )
                        }

                        is Result.Success<*> -> {
                            loginUser()
                        }

                        else -> Unit
                    }
                }
        }
    }


}

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val canLogin: Boolean = false,
    val hasErrors: Boolean = false,
    val loggedIn: Boolean = false,
    val errorMessage: String = ""
)