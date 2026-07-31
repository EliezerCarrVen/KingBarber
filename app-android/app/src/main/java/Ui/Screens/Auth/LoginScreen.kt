package Ui.Screens.Auth
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.itslerdo.barber.R
import com.itslerdo.barber.ui.theme.KingBarberGray
import com.itslerdo.barber.ui.theme.KingBarberNavy
import com.itslerdo.barber.ui.theme.KingBarberTheme

// TODO: reemplazar por los valores exactos que saques del panel "Inspect" de Figma

@Composable
fun LoginScreen(
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onLoginClick: (email: String, password: String) -> Unit,
    onGoogleClick: () -> Unit = {},
    onAppleClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val emailError = if (email.isNotEmpty() && !email.contains("@")) "Ingresa un correo válido" else null
    val passwordError = if (password.isNotEmpty() && password.length < 8) "La contraseña debe tener al menos 8 caracteres" else null

    val canSubmit = email.isNotBlank() && email.contains("@") && password.length >= 8 && !isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 48.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ---- Título ----
        Text(
            text = "KingBarber",
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Inicia sesión en tu cuenta",
            style = MaterialTheme.typography.bodyLarge,
            color = KingBarberGray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ---- Correo ----
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            singleLine = true,
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---- Contraseña ----
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            isError = passwordError != null,
            supportingText = { passwordError?.let { Text(it) } },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ---- Botón Iniciar sesión ----
        Button(
            onClick = { onLoginClick(email, password) },
            enabled = canSubmit,
            colors = ButtonDefaults.buttonColors(containerColor = KingBarberNavy),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Text("Iniciar sesión")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ---- Divisor "o" ----
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = "o",
                color = KingBarberGray,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ---- Continuar con Google ----
        SocialLoginButton(
            text = "Continuar con Google",
            iconResId = R.drawable.ic_google,
            onClick = onGoogleClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ---- Continuar con Apple ----
        SocialLoginButton(
            text = "Continuar con Apple",
            iconResId = R.drawable.ic_apple,
            onClick = onAppleClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ---- Link de registro ----
        val registerText = buildAnnotatedString {
            append("¿No tienes una cuenta? ")
            withStyle(style = SpanStyle(color = KingBarberNavy, fontWeight = FontWeight.SemiBold)) {
                append("Da clic para crear una")
            }
        }
        Text(
            text = registerText,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable { onRegisterClick() }
        )

        Spacer(modifier = Modifier.weight(1f))

        // ---- Footer: Términos y Privacidad ----
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Términos De Uso",
                style = MaterialTheme.typography.labelSmall,
                color = KingBarberGray,
                modifier = Modifier.clickable { onTermsClick() }
            )
            Text(
                text = "   ·   ",
                style = MaterialTheme.typography.labelSmall,
                color = KingBarberGray
            )
            Text(
                text = "Política de Privacidad",
                style = MaterialTheme.typography.labelSmall,
                color = KingBarberGray,
                modifier = Modifier.clickable { onPrivacyClick() }
            )
        }
    }
}

/**
 * Contenedor que maneja la lógica de autenticación (Google Sign-In, etc.)
 * y se comunica con el LoginScreen.
 */
@Composable
fun AuthScreenContainer(
    onLoginSuccess: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val clientId = stringResource(id = R.string.default_web_client_id)

    // TODO: Asegúrate de tener el archivo google-services.json en la carpeta /app
    // para que R.string.default_web_client_id esté disponible.
    val googleSignInClient = remember {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val tokenDeGoogle = account.idToken

                if (tokenDeGoogle != null) {
                    // TODO: Aquí envías 'tokenDeGoogle' a tu API en Node.js
                    println("Token listo para mandar a Node: $tokenDeGoogle")
                    onLoginSuccess()
                }

            } catch (e: ApiException) {
                println("Error al iniciar sesión con Google: ${e.message}")
            }
        }
    }

    LoginScreen(
        onGoogleClick = {
            googleSignInClient.signOut().addOnCompleteListener {
                launcher.launch(googleSignInClient.signInIntent)
            }
        },
        onLoginClick = { email, password ->
            // Aquí iría tu lógica normal de Login con correo/password
            println("Login con: $email / $password")
        },
        onRegisterClick = onRegisterClick
    )
}

/**
 * Botón reutilizable para los proveedores sociales (Google / Apple).
 */
@Composable
private fun SocialLoginButton(
    text: String,
    iconResId: Int,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = KingBarberNavy),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterStart)
            )
            Text(
                text = text,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    KingBarberTheme {
        LoginScreen(onLoginClick = { _, _ -> })
    }
}
 