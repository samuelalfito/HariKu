package com.hariku.feature_auth.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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