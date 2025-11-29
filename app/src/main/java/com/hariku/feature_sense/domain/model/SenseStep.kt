package com.hariku.feature_sense.domain.model

data class SenseStep(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val iconResName: String
)

object SenseStepsData {
    fun getAllSteps(): List<SenseStep> = listOf(
        SenseStep(
            stepNumber = 1,
            title = "Lihat 5 Hal",
            description = "Lihatlah ke sekelilingmu dan hitunglah lima hal yang dapat Kamu lihat dan ucapkan. Kamu mungkin dapat melihat orang berjalan, burung terbang di langit, atau air hujan. Tarik napas dalam-dalam di antara setiap hal.",
            iconResName = "see"
        ),
        SenseStep(
            stepNumber = 2,
            title = "Sentuh 4 Hal",
            description = "Lanjut dengan menyebutkan empat hal yang dapat Kamu sentuh. Mungkin Kamu sedang duduk di kursi dan Kamu bisa merasakan kursi di kaki atau punggungmu. Tarik napas dalam-dalam di antara setiap hal.",
            iconResName = "touch"
        ),
        SenseStep(
            stepNumber = 3,
            title = "Dengarkan 3 Hal",
            description = "Sebutkan tiga hal yang dapat Kamu dengar. Baik di dalam maupun di luar ruangan. Apa pun itu, selama Anda bisa mendengarnya, itu penting. Tarik napas dalam-dalam di antara setiap hal.",
            iconResName = "hear"
        ),
        SenseStep(
            stepNumber = 4,
            title = "Cium Bau 2 Hal",
            description = "Sebutkan dua benda yang dapat kamu cium. Lakukan hal ini dengan mata tertutup. Mungkin Kamu bisa mencium aroma makanan, aroma bunga, atau pengharum ruangan di dekatmu. Tarik napas dalam-dalam di antara setiap hal.",
            iconResName = "nose"
        ),
        SenseStep(
            stepNumber = 5,
            title = "Rasakan 1 Hal",
            description = "Sebutkan satu hal yang bisa Kamu cicipi. Kamu mungkin bisa merasakan kopi yang Kamu minum pagi ini, atau sarapanmu. Mungkin Kamu bisa merasakan cokelat atau jeruk dari sarapan Anda. Tarik napas dalam-dalam di antara setiap hal.",
            iconResName = "taste"
        )
    )
    
    const val TOTAL_STEPS = 6
}

