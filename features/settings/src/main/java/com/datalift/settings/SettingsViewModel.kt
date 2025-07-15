package com.datalift.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datalift.data.repository.CredentialsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val credentialRepository: CredentialsRepository
) : ViewModel() {

    fun signOut(){
        viewModelScope.launch {
            credentialRepository.signOut()
        }
    }
}