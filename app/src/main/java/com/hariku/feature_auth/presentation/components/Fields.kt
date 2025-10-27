package com.hariku.feature_auth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegularTextField(text: String, onValueChange: (String) -> Unit, isPassword: Boolean = false) {

    val orangeColor = Color(0xFFCD8C63)
    val lightOrangeColor = Color(0xFFFFF5EE)
    val darkOrangeColor = Color(0xFFB97A52)

    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                "example@mail.com",
                color = Color.Gray.copy(alpha = 0.6f)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = orangeColor,
            unfocusedBorderColor = orangeColor.copy(alpha = 0.5f),
            focusedContainerColor = lightOrangeColor.copy(alpha = 0.3f),
            unfocusedContainerColor = lightOrangeColor.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        visualTransformation = if (isPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )
}