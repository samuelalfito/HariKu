package com.hariku.feature_auth.presentation.login

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.R
import com.hariku.core.ui.components.Routes
import com.hariku.feature_auth.presentation.components.AuthDivider
import com.hariku.feature_auth.presentation.components.RegularTextField
import com.hariku.feature_auth.presentation.components.TextLogo
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@SuppressLint("ContextCastToActivity")
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val orangeColor = Color(0xFFCD8C63)
    val context = LocalContext.current as Activity
    val googleAuthUiClient = remember(context) { //Key nya context, kalau context berubah buat ulang
        GoogleAuthUiClient(context = context)
    }
    val scope = rememberCoroutineScope()


    LaunchedEffect(key1 = uiState) {
        if (uiState.loginSuccess) {
            // SUKSES! Arahkan ke PIN_GRAPH
            navController.navigate(Routes.PIN_GRAPH) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }

        if (uiState.error != null) {
            Log.e("LoginScreen", "Error: ${uiState.error}")
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

            // Form
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                RegularTextField(
                    text = uiState.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    placeholder = "Email "
                )

                Spacer(modifier = Modifier.height(16.dp))

                RegularTextField(
                    text = uiState.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    isPassword = true,
                    placeholder = "Password"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        /*TODO: LOGIN FEATURE
                                PIN Verification, if have PIN go to Routes.MASUKKAN_PIN*/
                        Log.d("HariKu:LoginScreen", "Login")
                        viewModel.onLoginClicked()
                    },
                    enabled = !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = orangeColor
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            text = "Login",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            AuthDivider()

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    scope.launch {
                        val tokenCredential = googleAuthUiClient.signIn()

                        if (tokenCredential != null) {
                            viewModel.onGoogleSignInSuccess(tokenCredential.idToken)
                        } else {
                            viewModel.onGoogleSignInFailed("Login Google dibatalkan atau gagal.")
                        }
                    }
                },
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

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Pengguna baru? ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(0.dp)
                )
                Text(
                    text = "Daftar di sini",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE0A071),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .padding(0.dp)
                        .clickable {
                            Log.d("DEBUG", "Daftar")
                            navController.navigate(Routes.REGISTER)
                        }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

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
                Text(
                    text = "Masuk Sebagai Tamu",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        navController = rememberNavController(),
        viewModel = viewModel()
    )
}