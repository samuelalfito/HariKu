package com.hariku.feature_auth.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.hariku.feature_auth.presentation.components.AuthDivider
import com.hariku.feature_auth.presentation.components.RegularTextField
import com.hariku.feature_auth.presentation.components.TextLogo
import com.hariku.R
import com.hariku.core.ui.theme.HariKuTheme

@Composable
fun RegisterScreen() {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val orangeColor = Color(0xFFCD8C63)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(20.dp)
    ){
        Spacer(modifier = Modifier.height(84.dp))
        TextLogo(borderPreview = true)
        Spacer(modifier = Modifier.height(32.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            RegularTextField(
                text = email,
                onValueChange = { email = it },
                placeholder = "Email atau Nomor Telepon"
            )

            Spacer(modifier = Modifier.height(16.dp))

            RegularTextField(
                text = name,
                onValueChange = { name = it },
                placeholder = "Nama"
            )

            Spacer(modifier = Modifier.height(16.dp))

            RegularTextField(
                text = password,
                onValueChange = { password = it },
                isPassword = true,
                placeholder = "Kata Sandi (Minimal 8 Karakter)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            RegularTextField(
                text = confirmPassword,
                onValueChange = { confirmPassword = it },
                isPassword = true,
                placeholder = "Konfirmasi Kata Sandi"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = orangeColor
                )
            ) {
                Text(
                    text = "Daftar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
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
        ){
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
            onLoginClick = { }
        )
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
    onLoginClick : () -> Unit
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
    HariKuTheme  {
        RegisterScreen()
    }
}