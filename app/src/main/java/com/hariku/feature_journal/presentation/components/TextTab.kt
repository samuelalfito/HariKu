package com.hariku.feature_journal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.feature_journal.domain.model.TextElement
import com.hariku.core.ui.theme.Blue100
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.Coral40
import com.hariku.core.ui.theme.Neutral0
import com.hariku.core.ui.theme.Neutral100
import com.hariku.core.ui.theme.Purple30
import com.hariku.core.ui.theme.Neutral75
import com.hariku.core.ui.theme.SpecialGreen
import com.hariku.core.ui.theme.SpecialOrange

@Composable
fun TextTab(
    selectedTextElement: TextElement?,
    onAddText: () -> Unit,
    onUpdateTextProperty: ((TextElement) -> TextElement) -> Unit,
) {
    var showColorPicker by remember { mutableStateOf(false) }
    var showFontPicker by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text formatting tools header
        if (selectedTextElement != null) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "TEKS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AdaptiveColors.adaptiveTextSecondary(),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                // Font selector and tools
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Font dropdown
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, AdaptiveColors.adaptiveDivider(), RoundedCornerShape(8.dp))
                            .clickable {
                                showFontPicker = !showFontPicker
                                showColorPicker = false
                            }
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedTextElement.fontFamily,
                            fontSize = 14.sp,
                            color = AdaptiveColors.adaptiveText()
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Coral40
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    // Text styling buttons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Color picker
                        IconButton(
                            onClick = {
                                showColorPicker = !showColorPicker
                                showFontPicker = false
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .border(1.dp, Coral40, CircleShape)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(selectedTextElement.color)
                                    .border(1.dp, AdaptiveColors.adaptiveDivider(), CircleShape)
                            )
                        }
                        
                        // Text alignment
                        IconButton(
                            onClick = {
                                selectedTextElement.let { it ->
                                    val nextAlign = when (it.textAlign) {
                                        TextAlign.Start ->
                                            TextAlign.Center
                                        
                                        TextAlign.Center ->
                                            TextAlign.End
                                        
                                        else -> TextAlign.Start
                                    }
                                    onUpdateTextProperty { it.copy(textAlign = nextAlign) }
                                }
                            },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = when (selectedTextElement.textAlign) {
                                    TextAlign.Center -> Icons.AutoMirrored.Filled.KeyboardArrowRight
                                    TextAlign.End -> Icons.Default.KeyboardArrowDown
                                    else -> Icons.Default.KeyboardArrowUp
                                },
                                contentDescription = "Align",
                                tint = AdaptiveColors.adaptiveText()
                            )
                        }
                        
                        // Font size
                        IconButton(
                            onClick = {
                                selectedTextElement.let { it ->
                                    val newSize = if (it.fontSize >= 36f) 16f else it.fontSize + 4f
                                    onUpdateTextProperty { it.copy(fontSize = newSize) }
                                }
                            },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Size",
                                tint = AdaptiveColors.adaptiveText()
                            )
                        }
                        
                        // Underline
                        IconButton(
                            onClick = {
                                onUpdateTextProperty { it.copy(isUnderlined = !it.isUnderlined) }
                            },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Underline",
                                tint = if (selectedTextElement.isUnderlined)
                                    Coral40
                                else AdaptiveColors.adaptiveText()
                            )
                        }
                    }
                }
                
                // Color picker
                if (showColorPicker) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(8),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    ) {
                        val colors = listOf(
                            Neutral100,
                            Neutral0,
                            Color.Red,
                            Color.Blue,
                            Color.Green,
                            Color.Yellow,
                            Color.Magenta,
                            Color.Cyan,
                            SpecialOrange,
                            Purple30,
                            Blue100,
                            SpecialGreen
                        )
                        items(colors.size) { index ->
                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clip(CircleShape)
                                    .background(colors[index])
                                    .border(
                                        width = if (selectedTextElement.color == colors[index]) 2.dp else 1.dp,
                                        color = if (selectedTextElement.color == colors[index])
                                            Coral40
                                        else
                                            AdaptiveColors.adaptiveDivider(),
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        onUpdateTextProperty { it.copy(color = colors[index]) }
                                        showColorPicker = false
                                    }
                            )
                        }
                    }
                }
                
                // Font picker
                if (showFontPicker) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .border(1.dp, AdaptiveColors.adaptiveDivider(), RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val fonts = listOf("Default", "Serif", "Sans Serif", "Monospace", "Cursive")
                        fonts.forEach { font ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        if (selectedTextElement.fontFamily == font)
                                            Coral40.copy(alpha = 0.2f)
                                        else
                                            Color.Transparent
                                    )
                                    .clickable {
                                        onUpdateTextProperty { it.copy(fontFamily = font) }
                                        showFontPicker = false
                                    }
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = font,
                                    fontSize = 14.sp,
                                    color = if (selectedTextElement.fontFamily == font)
                                        Coral40
                                    else
                                        AdaptiveColors.adaptiveText()
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Shadow settings
                Text(
                    text = "BAYANGAN",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AdaptiveColors.adaptiveTextSecondary(),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Shadow X
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "X", fontSize = 12.sp, color = AdaptiveColors.adaptiveTextSecondary())
                        Text(
                            text = "${selectedTextElement.shadowX.toInt()} px",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AdaptiveColors.adaptiveText(),
                            modifier = Modifier
                                .clickable {
                                    selectedTextElement.let { it ->
                                        val newX = if (it.shadowX >= 10f) -10f else it.shadowX + 2f
                                        onUpdateTextProperty { it.copy(shadowX = newX) }
                                    }
                                }
                                .padding(4.dp)
                        )
                    }
                    
                    // Shadow Y
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Y", fontSize = 12.sp, color = AdaptiveColors.adaptiveTextSecondary())
                        Text(
                            text = "${selectedTextElement.shadowY.toInt()} px",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AdaptiveColors.adaptiveText(),
                            modifier = Modifier
                                .clickable {
                                    selectedTextElement.let { it ->
                                        val newY = if (it.shadowY >= 10f) -10f else it.shadowY + 2f
                                        onUpdateTextProperty { it.copy(shadowY = newY) }
                                    }
                                }
                                .padding(4.dp)
                        )
                    }
                    
                    // Shadow Radius
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Radius", fontSize = 12.sp, color = AdaptiveColors.adaptiveTextSecondary())
                        Text(
                            text = "${selectedTextElement.shadowRadius.toInt()} px",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AdaptiveColors.adaptiveText(),
                            modifier = Modifier
                                .clickable {
                                    selectedTextElement.let { it ->
                                        val newRadius =
                                            if (it.shadowRadius >= 20f) 0f else it.shadowRadius + 4f
                                        onUpdateTextProperty { it.copy(shadowRadius = newRadius) }
                                    }
                                }
                                .padding(4.dp)
                        )
                    }
                    
                    // Shadow Color
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(selectedTextElement.shadowColor)
                            .border(2.dp, Coral40, CircleShape)
                            .clickable {
                                selectedTextElement.let { it ->
                                    val newColor = if (it.shadowColor == Neutral0)
                                        Neutral100
                                    else
                                        Neutral0
                                    onUpdateTextProperty { it.copy(shadowColor = newColor) }
                                }
                            }
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Outline settings
                Text(
                    text = "GARIS LUAR",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AdaptiveColors.adaptiveTextSecondary(),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Opacity
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Opacity", fontSize = 14.sp, color = AdaptiveColors.adaptiveTextSecondary())
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${(selectedTextElement.shadowOpacity * 100).toInt()} %",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AdaptiveColors.adaptiveText(),
                            modifier = Modifier.clickable {
                                selectedTextElement.let { it ->
                                    val newOpacity =
                                        if (it.shadowOpacity >= 1f) 0.2f else it.shadowOpacity + 0.2f
                                    onUpdateTextProperty { it.copy(shadowOpacity = newOpacity) }
                                }
                            }
                        )
                    }
                    
                    // Outline Width
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Ketebalan", fontSize = 14.sp, color = AdaptiveColors.adaptiveTextSecondary())
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${selectedTextElement.outlineWidth.toInt()} px",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AdaptiveColors.adaptiveText(),
                            modifier = Modifier.clickable {
                                selectedTextElement.let { it ->
                                    val newWidth =
                                        if (it.outlineWidth >= 8f) 0f else it.outlineWidth + 2f
                                    onUpdateTextProperty { it.copy(outlineWidth = newWidth) }
                                }
                            }
                        )
                    }
                    
                    // Outline Color
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(selectedTextElement.outlineColor)
                            .border(2.dp, Coral40, CircleShape)
                            .clickable {
                                selectedTextElement.let { it ->
                                    val newColor = if (it.outlineColor == Neutral0)
                                        Neutral100
                                    else
                                        Neutral0
                                    onUpdateTextProperty { it.copy(outlineColor = newColor) }
                                }
                            }
                    )
                }
            }
        } else {
            // Add text button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = onAddText,
                    modifier = Modifier
                        .size(64.dp)
                        .background(Coral40, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add text",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tambah/Pilih Teks",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Coral40
                )
            }
        }
    }
}
