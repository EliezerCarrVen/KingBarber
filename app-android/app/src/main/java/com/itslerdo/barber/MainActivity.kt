package com.itslerdo.barber

import Ui.Screens.Auth.AuthScreenContainer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.itslerdo.barber.ui.theme.KingBarberTheme

import Ui.Screens.Auth.RegisterScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KingBarberTheme {
                var currentScreen by remember { mutableStateOf("login") }

                when (currentScreen) {
                    "login" -> {
                        AuthScreenContainer(
                            onLoginSuccess = {
                                // Navegar a la pantalla principal
                            },
                            onRegisterClick = {
                                currentScreen = "register"
                            }
                        )
                    }
                    "register" -> {
                        RegisterScreen(
                            onCreateAccountClick = { formData ->
                                // TODO: Llamar al ViewModel y registrar la cuenta
                            },
                            onLoginClick = {
                                currentScreen = "login"
                            }
                        )
                    }
                }
            }
        }
    }
}
