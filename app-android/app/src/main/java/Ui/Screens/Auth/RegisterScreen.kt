package Ui.Screens.Auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itslerdo.barber.ui.theme.KingBarberGray
import com.itslerdo.barber.ui.theme.KingBarberNavy
import com.itslerdo.barber.ui.theme.KingBarberTheme
import com.itslerdo.barber.R

// TODO: reemplazar por los valores exactos del panel "Inspect" de Figma

/**
 * Datos capturados por el formulario de registro.
 * onCreateAccountClick los entrega ya armados para que el ViewModel
 * los mande al endpoint POST /auth/register del backend en node/.
 */
data class RegisterFormData(
    val email: String,
    val nombre: String,
    val apellido: String,
    val direccion: String,
    val dia: String,
    val mes: String,
    val anio: String,
    val password: String
)

@Composable
fun RegisterScreen(
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onCreateAccountClick: (RegisterFormData) -> Unit,
    onLoginClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var dia by remember { mutableStateOf("") }
    var mes by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }

    val emailError = if (email.isNotEmpty() && !email.contains("@")) "Ingresa un correo válido" else null
    val passwordError = if (password.isNotEmpty() && password.length < 8) "La contraseña debe tener al menos 8 caracteres" else null

    val camposObligatoriosLlenos = email.isNotBlank() && email.contains("@") && nombre.isNotBlank() &&
        apellido.isNotBlank() && dia.isNotBlank() && mes.isNotBlank() && anio.isNotBlank() && password.length >= 8
    val canSubmit = camposObligatoriosLlenos && termsAccepted && !isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(top = 40.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ---- Título ----
        Text(text = "KingBarber", fontSize = 28.sp, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Crea tu cuenta",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(28.dp))

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

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(20.dp))

        // ---- Fecha de cumpleaños ----
        Text(
            text = "Fecha de cumpleaños",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = dia,
                onValueChange = { if (it.length <= 2) dia = it.filter(Char::isDigit) },
                label = { Text("Día (DD)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = mes,
                onValueChange = { if (it.length <= 2) mes = it.filter(Char::isDigit) },
                label = { Text("Mes (MM)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = anio,
                onValueChange = { if (it.length <= 4) anio = it.filter(Char::isDigit) },
                label = { Text("Año (AAAA)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1.3f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ---- Checkbox de términos ----
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = { termsAccepted = it }
            )
            val termsText = buildAnnotatedString {
                append("He leído y acepto los ")
                withStyle(style = SpanStyle(color = KingBarberNavy, fontWeight = FontWeight.SemiBold)) {
                    append("Términos de Uso")
                }
                append(" y ")
                withStyle(style = SpanStyle(color = KingBarberNavy, fontWeight = FontWeight.SemiBold)) {
                    append("Política de Privacidad")
                }
            }
            Text(
                text = termsText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clickable { termsAccepted = !termsAccepted }
            )
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ---- Botón Crear cuenta ----
        Button(
            onClick = {
                onCreateAccountClick(
                    RegisterFormData(email, nombre, apellido, direccion, dia, mes, anio, password)
                )
            },
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
                Text("Crear cuenta")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ---- Link a login ----
        val loginText = buildAnnotatedString {
            append("¿Ya tienes una cuenta? ")
            withStyle(style = SpanStyle(color = KingBarberNavy, fontWeight = FontWeight.SemiBold)) {
                append("Inicia sesión")
            }
        }
        Text(
            text = loginText,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable { onLoginClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    KingBarberTheme {
        RegisterScreen(onCreateAccountClick = {})
    }
}
