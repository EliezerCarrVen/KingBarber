package com.itslerdo.barber

import Ui.Screens.Auth.LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.itslerdo.barber.ui.theme.KingBarberTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KingBarberTheme {
                LoginScreen(
                    onLoginClick = { email, password ->
                        // TODO: Conectar con el ViewModel o API más adelante.
                        // Parámetros: $email, $password
                    }
                )
            }
        }
    }
}
