package com.hariku.feature_statistic.presentation.components

import androidx.compose.ui.graphics.Color
import com.hariku.R

enum class Mood(val iconRes: Int, val color: Color) {
    SENANG(R.drawable.ic_emote_senang, Color(0xFFFFD481)),
    SEMANGAT(R.drawable.ic_emote_semangat, Color(0xFFFFCEA2)),
    BIASA(R.drawable.ic_emote_biasa, Color(0xFFE9C9AC)),
    SEDIH(R.drawable.ic_emote_sedih, Color(0xFFB0E6ED)),
    MARAH(R.drawable.ic_emote_marah, Color(0xFFF1A69A)),
    TAKUT(R.drawable.ic_emote_takut, Color(0xFFADDBB5)),
    CEMAS(R.drawable.ic_emote_cemas, Color(0xFFD5C5F5)),
    KECEWA(R.drawable.ic_emote_kecewa, Color(0xFFD1EDAE)),
    LELAH(R.drawable.ic_emote_lelah, Color(0xFFE9CAEC)),
    HAMPA(R.drawable.ic_emote_hampa, Color(0xFFDBE0DF)),
    NONE(R.drawable.ic_cross, Color(0xFF9F9F9F))
}