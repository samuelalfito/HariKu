package com.hariku.feature_auth.presentation.register

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.feature_auth.presentation.components.AuthDivider
import com.hariku.feature_auth.presentation.components.RegularTextField
import com.hariku.feature_auth.presentation.components.TextLogo
import com.hariku.R
import com.hariku.core.ui.components.Routes
import com.hariku.core.ui.theme.HariKuTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterScreenViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var errorMessage by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val orangeColor = Color(0xFFCD8C63)

    if(uiState.error != null && uiState.error != ""){ errorMessage = uiState.error!! }

    // --- TAMBAHAN: Menangani navigasi dan error ---
    LaunchedEffect(key1 = uiState) {
        if (uiState.registerSuccess) {
            // SUKSES! Arahkan ke PIN_GRAPH
            navController.navigate(Routes.PinGraph.route) {
                // Hapus tumpukan navigasi auth agar user tidak bisa kembali
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }

        if (uiState.error != null) {
            // ADA ERROR! Tampilkan Snackbar atau Toast di sini
            Log.e("RegisterScreen", "Error: ${uiState.error}")
            // Beri tahu ViewModel bahwa error sudah ditampilkan
            viewModel.onErrorShown()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(470.dp)
                .offset(y = 77.41.dp)
                .background(
                    color = Color(0xFFFAF2ED),
                    shape = CircleShape
                )
                .align(Alignment.BottomCenter)
        )
        Image(
            painter = painterResource(id = R.drawable.auth_cat),
            contentDescription = "Google Icon",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(84.dp))
            TextLogo(borderPreview = true)
            Spacer(modifier = Modifier.height(32.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                RegularTextField(
                    text = uiState.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    placeholder = "Email atau Nomor Telepon"
                )

                Spacer(modifier = Modifier.height(16.dp))

                RegularTextField(
                    text = uiState.name,
                    onValueChange = { viewModel.onNameChange(it) },
                    placeholder = "Nama"
                )

                Spacer(modifier = Modifier.height(16.dp))

                RegularTextField(
                    text = uiState.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    isPassword = true,
                    placeholder = "Kata Sandi (Minimal 8 Karakter)"
                )

                Spacer(modifier = Modifier.height(16.dp))

                RegularTextField(
                    text = uiState.confirmPassword,
                    onValueChange = { viewModel.onConfirmPasswordChange(it) },
                    isPassword = true,
                    placeholder = "Konfirmasi Kata Sandi"
                )

                Text(
                    text = errorMessage,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.padding(horizontal = 40.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        /*TODO REGISTER FEATURE*/
                        viewModel.onRegisterClicked()
                    },
                    enabled = !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = orangeColor
                    )
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Daftar",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TermsAndPrivacyText(
                onTermsClick = { },
                onPrivacyClick = { }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AuthDivider()

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Masuk Dengan Google",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AlreadyHaveAccount(
                onLoginClick = {
                    navController.popBackStack(route = Routes.Login.route, inclusive = false)
                }
            )
        }
    }
}

@Composable
fun TermsAndPrivacyText(
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
    val annotatedText = buildAnnotatedString {
        append("Dengan mendaftar pada aplikasi Urai, saya telah menyetujui ")

        pushStringAnnotation(tag = "TERMS", annotation = "terms")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = Color(0xFFE0A071)
            )
        ) {
            append("Ketentuan Layanan")
        }
        pop()

        append(" dan ")

        pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = Color(0xFFE0A071)
            )
        ) {
            append("Kebijakan Privasi")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        style = TextStyle(
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier.fillMaxWidth(),
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "TERMS", start = offset, end = offset)
                .firstOrNull()?.let { onTermsClick() }

            annotatedText.getStringAnnotations(tag = "PRIVACY", start = offset, end = offset)
                .firstOrNull()?.let { onPrivacyClick() }
        }
    )
}


@Composable
fun AlreadyHaveAccount(
    onLoginClick: () -> Unit
) {
    val loginText = buildAnnotatedString {
        append("Punya akun? ")

        pushStringAnnotation(tag = "LOGIN", annotation = "login")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = Color(0xFFE0A071)
            )
        ) { append("Masuk") }
        pop()
    }

    ClickableText(
        text = loginText,
        style = TextStyle(
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier.fillMaxWidth(),
        onClick = { offset ->
            loginText.getStringAnnotations("LOGIN", offset, offset)
                .firstOrNull()?.let { onLoginClick() }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    HariKuTheme {
        RegisterScreen(rememberNavController())
    }
}