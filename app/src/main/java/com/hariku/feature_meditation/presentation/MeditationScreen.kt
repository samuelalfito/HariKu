package com.hariku.feature_meditation.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R

@Composable
fun MeditationScreen() {
    val categories = listOf(
        "Cemas" to listOf(
            R.drawable.cemas_tenangkan_diri to "Tenangkan Diri",
            R.drawable.cemas_mengapa_cemas to "Mengapa Cemas",
            R.drawable.cemas_tempat_aman to "Tempat Aman",
            R.drawable.cemas_alihkan_fokus to "Alihkan Fokus",
            R.drawable.cemas_melepaskan to "Melepaskan",
            R.drawable.cemas_putar_balik_waktu to "Putar Balik Waktu",
            R.drawable.cemas_the_triggers to "The Triggers"
        ),
        "Positivitas" to listOf(
            R.drawable.positivitas_kualitas_diri to "Kualitas Diri",
            R.drawable.positivitas_optimisme to "Optimisme",
            R.drawable.positivitas_hal_baik to "Hal-Hal Baik",
            R.drawable.positivitas_apresiasi to "Apresiasi",
            R.drawable.positivitas_mengatasi_fomo to "Mengatasi FOMO"
        ),
        "Produktivitas" to listOf(
            R.drawable.produktivitas_latihan_fokus to "Latihan Fokus",
            R.drawable.produktivitas_kekuranganku to "Kekuranganku",
            R.drawable.produktivitas_rencana_kerja to "Rencana Kerja",
            R.drawable.produktivitas_rentang_perhatian to "Rentang Perhatian"
        ),
        "Tidur" to listOf(
            R.drawable.tidur_sebelum_tidur to "Sebelum Tidur",
            R.drawable.tidur_pikiran_jernih to "Pikiran Jernih",
            R.drawable.tidur_mimpi_buruk to "Mimpi Buruk",
            R.drawable.tidur_kualitas_tidur to "Kualitas Tidur"
        ),
        "Marah" to listOf(
            R.drawable.marah_relaksasi to "Relaksasi",
            R.drawable.marah_manajemen_emosi to "Manajemen Emosi",
            R.drawable.marah_gunung_es_emosi to "Gunung Es Emosi"
        ),
        "Stress" to listOf(
            R.drawable.stress_work_life_balance to "Work-Life Balance",
            R.drawable.stress_ubah_pandangan to "Ubah Pandangan",
            R.drawable.stress_langkah_kecil to "Langkah Kecil",
            R.drawable.stress_awan_pikiran to "Awan Pikiran"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFCFC))
            .verticalScroll(rememberScrollState())
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = "Panduan Meditasi",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF242424),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp)
        )

        categories.forEach { (category, items) ->
            CategorySection(title = category, items = items)
        }
    }
}

@Composable
fun CategorySection(title: String, items: List<Pair<Int, String>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color(0xFFEFEFEF), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF242424),
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
        ) {
            items.forEach { (imageRes, label) ->
                MeditationCard(imageRes, label)
            }
        }
    }
}

@Composable
fun MeditationCard(imageRes: Int, label: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .width(120.dp)
            .padding(end = 12.dp)
            .clickable {  }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = label,
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF242424)
            )
        }
    }
}