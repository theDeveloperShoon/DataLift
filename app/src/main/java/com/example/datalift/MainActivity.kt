package com.example.datalift

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.datalift.data.repository.UserRepository
import com.datalift.database.service.AccountService
import com.datalift.designsystem.theme.DataliftTheme
import com.example.datalift.ui.DataliftApp
import com.example.datalift.ui.rememberDataliftAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var accountService: AccountService

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val appState = rememberDataliftAppState(
                userRepository = userRepository,
                accountService = accountService
            )

            DataliftTheme {
                DataliftApp(appState = appState)
            }
        }
    }
}