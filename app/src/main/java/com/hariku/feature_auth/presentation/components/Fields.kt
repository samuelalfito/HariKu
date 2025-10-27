package com.hariku.feature_auth.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegularTextField(text: String, onValueChange: (String) -> Unit, isPassword: Boolean = false, placeholder: String) {
    var passwordVisible by remember { mutableStateOf(false) }

    val orangeColor = Color(0xFFE0A071)
    val lightOrangeColor = Color(0xFFE0A071)

    val keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Email
    val visualTransformation = if (isPassword && !passwordVisible) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(fontSize = 16.sp, color = Color(0xFF9F9F9F))
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = orangeColor,
            unfocusedBorderColor = orangeColor.copy(alpha = 0.5f),
            focusedContainerColor = lightOrangeColor.copy(alpha = 0.1f),
            unfocusedContainerColor = lightOrangeColor.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        visualTransformation = visualTransformation,
        textStyle = TextStyle(fontSize = 18.sp, color = Color(0xFF333333)),
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    if (passwordVisible) {
                        Text(text = "Sembunyikan", style = TextStyle(fontSize = 14.sp, color = Color(0xFF333333)))
                    } else {
                        Image(
                            painter = painterResource(id = com.hariku.R.drawable.show_password),
                            contentDescription = "Tampilkan kata sandi",
                            modifier = Modifier.size(33.dp)
                        )
                    }
                }
            }
        } else null
    )
}

@Composable
fun AuthDivider() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            thickness = 2.dp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "ATAU",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        HorizontalDivider(
            thickness = 2.dp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
    }
}