package com.hariku.feature_journal.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hariku.R
import com.hariku.core.ui.theme.AdaptiveColors

@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    OutlinedTextField(
        value = value,
        singleLine = true,
        onValueChange = { onValueChange(it) },
        placeholder = { 
            Text(
                placeholder, 
                color = AdaptiveColors.adaptiveTextSecondary()
            ) 
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = AdaptiveColors.adaptiveTextSecondary()
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = AdaptiveColors.adaptiveDivider(),
            focusedContainerColor = AdaptiveColors.adaptiveDivider(),
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedTextColor = AdaptiveColors.adaptiveText(),
            unfocusedTextColor = AdaptiveColors.adaptiveText()
        )
    )
}
