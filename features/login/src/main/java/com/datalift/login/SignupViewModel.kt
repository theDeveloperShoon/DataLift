package com.datalift.login

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
class SignupViewModel @Inject constructor(
    private val credentialsRepository: CredentialsRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    private fun checkCanSignup(): Boolean{
        return _uiState.value.username.isNotEmpty()
                && _uiState.value.password.length >= 6
    }

    fun updateEmail(email: String){
        _uiState.update { currentState ->
            currentState.copy(
                username = email,
                canSignup = checkCanSignup(),
                errorMessage = "",
                hasErrors = false
            )
        }
    }

    fun updatePassword(password: String){
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                canSignup = checkCanSignup(),
                errorMessage = "",
                hasErrors = false
            )
        }
    }

    private fun updateError(errorMessage: String = ""){
        _uiState.update { currentState ->
            currentState.copy(
                hasErrors = true,
                errorMessage = errorMessage
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

    fun signUp(){
        viewModelScope.launch {
            credentialsRepository.createUserEmailAndPassword(
                email = _uiState.value.username,
                password = _uiState.value.password
            ).collect { result ->
                when(result){
                    is Result.Error -> {
                        updateError(result.exception.message ?: "Unknown Error")
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

data class SignupUiState(
    val username: String = "",
    val password: String = "",
    val canSignup: Boolean = false,
    val hasErrors: Boolean = false,
    val loggedIn: Boolean = false,
    val errorMessage: String = ""
)