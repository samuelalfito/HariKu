package com.hariku.feature_auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.feature_auth.presentation.components.AuthDivider
import com.hariku.feature_auth.presentation.components.RegularTextField
import com.hariku.feature_auth.presentation.components.TextLogo



@Composable
fun LoginScreen() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val orangeColor = Color(0xFFCD8C63)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(20.dp)
    ){
        Spacer(modifier = Modifier.height(128.dp))
        TextLogo(borderPreview = true)
        Spacer(modifier = Modifier.height(64.dp))

        // Form
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            RegularTextField(
                text = email,
                onValueChange = {email = it},
                placeholder = "Email "
            )

            Spacer(modifier = Modifier.height(16.dp))

            RegularTextField(
                text = password,
                onValueChange = {password = it},
                isPassword = true,
                placeholder = "Password"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Handle login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = orangeColor
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        AuthDivider()

        Spacer(modifier = Modifier.height(32.dp))


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}