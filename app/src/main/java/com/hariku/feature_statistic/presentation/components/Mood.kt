package com.hariku.feature_statistic.presentation.components

import androidx.compose.ui.graphics.Color
import com.hariku.R
import com.hariku.core.ui.theme.Blue50
import com.hariku.core.ui.theme.Green60
import com.hariku.core.ui.theme.Green80
import com.hariku.core.ui.theme.Neutral85
import com.hariku.core.ui.theme.Purple70
import com.hariku.core.ui.theme.Purple80
import com.hariku.core.ui.theme.Yellow60
import com.hariku.core.ui.theme.Yellow70
import com.hariku.core.ui.theme.MoodNeutral
import com.hariku.core.ui.theme.MoodAngry
import com.hariku.core.ui.theme.MoodEmpty

enum class Mood(val iconRes: Int, val color: Color) {
    SENANG(R.drawable.ic_emote_senang, Yellow60),
    SEMANGAT(R.drawable.ic_emote_semangat, Yellow70),
    BIASA(R.drawable.ic_emote_biasa, MoodNeutral),
    SEDIH(R.drawable.ic_emote_sedih, Blue50),
    MARAH(R.drawable.ic_emote_marah, MoodAngry),
    TAKUT(R.drawable.ic_emote_takut, Green60),
    CEMAS(R.drawable.ic_emote_cemas, Purple70),
    KECEWA(R.drawable.ic_emote_kecewa, Green80),
    LELAH(R.drawable.ic_emote_lelah, Purple80),
    HAMPA(R.drawable.ic_emote_hampa, MoodEmpty),
    NONE(R.drawable.ic_cross, Neutral85)
}