package com.hariku.feature_meditation.data.remote

import com.hariku.feature_meditation.data.dto.MeditationSongDto

class MeditationRemoteDataSource {
    
    suspend fun getAllSongs(): List<MeditationSongDto> {
        return getMeditationSongsData()
    }
    
    private fun getMeditationSongsData(): List<MeditationSongDto> {
        return listOf(
            // Kategori: Cemas
            MeditationSongDto(
                id = "cemas_tenangkan_diri",
                title = "Tenangkan Diri",
                category = "Cemas",
                imageResName = "cemas_tenangkan_diri",
                audioResName = "meditation_music", // Semua menggunakan lagu yang sama
                durationMs = 300000L,
                description = "Meditasi untuk menenangkan diri saat cemas"
            ),
            MeditationSongDto(
                id = "cemas_mengapa_cemas",
                title = "Mengapa Cemas",
                category = "Cemas",
                imageResName = "cemas_mengapa_cemas",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Memahami penyebab kecemasan"
            ),
            MeditationSongDto(
                id = "cemas_tempat_aman",
                title = "Tempat Aman",
                category = "Cemas",
                imageResName = "cemas_tempat_aman",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Membayangkan tempat aman untuk menenangkan diri"
            ),
            MeditationSongDto(
                id = "cemas_alihkan_fokus",
                title = "Alihkan Fokus",
                category = "Cemas",
                imageResName = "cemas_alihkan_fokus",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Teknik mengalihkan fokus dari kecemasan"
            ),
            MeditationSongDto(
                id = "cemas_melepaskan",
                title = "Melepaskan",
                category = "Cemas",
                imageResName = "cemas_melepaskan",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Melepaskan beban pikiran yang mencemaskan"
            ),
            MeditationSongDto(
                id = "cemas_putar_balik_waktu",
                title = "Putar Balik Waktu",
                category = "Cemas",
                imageResName = "cemas_putar_balik_waktu",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Refleksi untuk memahami kecemasan"
            ),
            MeditationSongDto(
                id = "cemas_the_triggers",
                title = "The Triggers",
                category = "Cemas",
                imageResName = "cemas_the_triggers",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Mengenali pemicu kecemasan"
            ),
            
            // Kategori: Positivitas
            MeditationSongDto(
                id = "positivitas_kualitas_diri",
                title = "Kualitas Diri",
                category = "Positivitas",
                imageResName = "positivitas_kualitas_diri",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Menghargai kualitas diri sendiri"
            ),
            MeditationSongDto(
                id = "positivitas_optimisme",
                title = "Optimisme",
                category = "Positivitas",
                imageResName = "positivitas_optimisme",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Membangun sikap optimis"
            ),
            MeditationSongDto(
                id = "positivitas_hal_baik",
                title = "Hal-Hal Baik",
                category = "Positivitas",
                imageResName = "positivitas_hal_baik",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Fokus pada hal-hal positif dalam hidup"
            ),
            MeditationSongDto(
                id = "positivitas_apresiasi",
                title = "Apresiasi",
                category = "Positivitas",
                imageResName = "positivitas_apresiasi",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Belajar mengapresiasi diri dan sekitar"
            ),
            MeditationSongDto(
                id = "positivitas_mengatasi_fomo",
                title = "Mengatasi FOMO",
                category = "Positivitas",
                imageResName = "positivitas_mengatasi_fomo",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Mengatasi rasa takut ketinggalan"
            ),
            
            // Kategori: Produktivitas
            MeditationSongDto(
                id = "produktivitas_latihan_fokus",
                title = "Latihan Fokus",
                category = "Produktivitas",
                imageResName = "produktivitas_latihan_fokus",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Meningkatkan kemampuan fokus"
            ),
            MeditationSongDto(
                id = "produktivitas_kekuranganku",
                title = "Kekuranganku",
                category = "Produktivitas",
                imageResName = "produktivitas_kekuranganku",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Menerima dan memperbaiki kekurangan"
            ),
            MeditationSongDto(
                id = "produktivitas_rencana_kerja",
                title = "Rencana Kerja",
                category = "Produktivitas",
                imageResName = "produktivitas_rencana_kerja",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Membuat rencana kerja yang efektif"
            ),
            MeditationSongDto(
                id = "produktivitas_rentang_perhatian",
                title = "Rentang Perhatian",
                category = "Produktivitas",
                imageResName = "produktivitas_rentang_perhatian",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Memperluas rentang perhatian"
            ),
            
            // Kategori: Tidur
            MeditationSongDto(
                id = "tidur_sebelum_tidur",
                title = "Sebelum Tidur",
                category = "Tidur",
                imageResName = "tidur_sebelum_tidur",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Relaksasi sebelum tidur"
            ),
            MeditationSongDto(
                id = "tidur_pikiran_jernih",
                title = "Pikiran Jernih",
                category = "Tidur",
                imageResName = "tidur_pikiran_jernih",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Menjernihkan pikiran untuk tidur nyenyak"
            ),
            MeditationSongDto(
                id = "tidur_mimpi_buruk",
                title = "Mimpi Buruk",
                category = "Tidur",
                imageResName = "tidur_mimpi_buruk",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Mengatasi mimpi buruk"
            ),
            MeditationSongDto(
                id = "tidur_kualitas_tidur",
                title = "Kualitas Tidur",
                category = "Tidur",
                imageResName = "tidur_kualitas_tidur",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Meningkatkan kualitas tidur"
            ),
            
            // Kategori: Marah
            MeditationSongDto(
                id = "marah_relaksasi",
                title = "Relaksasi",
                category = "Marah",
                imageResName = "marah_relaksasi",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Meredakan amarah dengan relaksasi"
            ),
            MeditationSongDto(
                id = "marah_manajemen_emosi",
                title = "Manajemen Emosi",
                category = "Marah",
                imageResName = "marah_manajemen_emosi",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Mengelola emosi marah"
            ),
            MeditationSongDto(
                id = "marah_gunung_es_emosi",
                title = "Gunung Es Emosi",
                category = "Marah",
                imageResName = "marah_gunung_es_emosi",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Memahami lapisan emosi marah"
            ),
            
            // Kategori: Stress
            MeditationSongDto(
                id = "stress_work_life_balance",
                title = "Work-Life Balance",
                category = "Stress",
                imageResName = "stress_work_life_balance",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Menyeimbangkan kehidupan kerja"
            ),
            MeditationSongDto(
                id = "stress_ubah_pandangan",
                title = "Ubah Pandangan",
                category = "Stress",
                imageResName = "stress_ubah_pandangan",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Mengubah cara pandang terhadap stress"
            ),
            MeditationSongDto(
                id = "stress_langkah_kecil",
                title = "Langkah Kecil",
                category = "Stress",
                imageResName = "stress_langkah_kecil",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Mengatasi stress dengan langkah kecil"
            ),
            MeditationSongDto(
                id = "stress_awan_pikiran",
                title = "Awan Pikiran",
                category = "Stress",
                imageResName = "stress_awan_pikiran",
                audioResName = "meditation_music",
                durationMs = 300000L,
                description = "Membiarkan pikiran stres berlalu"
            )
        )
    }
}

