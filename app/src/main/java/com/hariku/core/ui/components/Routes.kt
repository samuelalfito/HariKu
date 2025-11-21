package com.hariku.core.ui.components

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(val route: String) {
    @Serializable
    object AuthGraph : Routes("auth_graph")
    @Serializable
    object Login : Routes("login")
    @Serializable
    object Register : Routes("register")

    @Serializable
    object Splash : Routes("splash")
    @Serializable
    object Onboarding : Routes("onboarding")

    @Serializable
    object PinGraph : Routes("pin_graph")
    @Serializable
    object TetapkanPin : Routes("tetapkan_pin")
    @Serializable
    object KonfirmasiPin : Routes("konfirmasi_pin")
    @Serializable
    object MasukkanPin : Routes("masukkan_pin")

    @Serializable
    object MainAppGraph : Routes("main_app_graph")
    @Serializable
    object Home : Routes("home")

    @Serializable
    object Chatbot : Routes("chatbot")
    @Serializable
    data class DetailChatbot(val chatbotId: String) : Routes("detail_chatbot/{chatbotId}") {
        companion object {
            fun createRoute(chatbotId: String) = "detail_chatbot/$chatbotId"
        }
    }
    @Serializable
    object CustomizeCat : Routes("customize_cat")
    @Serializable
    object CustomizeNewCat : Routes("customize_new_cat")
    @Serializable
    data class CustomizePersonalCat(val name: String, val avatarResId: Int) : Routes("customize_personal_cat/{name}/{avatarResId}") {
        companion object {
            fun createRoute(name: String, avatarResId: Int) = "customize_personal_cat/$name/$avatarResId"
        }
    }

    @Serializable
    object Journal : Routes("journal")
    @Serializable
    data class JournalDetail(val journalId: String) : Routes("journal_detail/{journalId}") {
        companion object {
            fun createRoute(journalId: String) = "journal_detail/$journalId"
        }
    }
    @Serializable
    object CreateJournal : Routes("create_journal")
    @Serializable
    object CreateNotePrompt : Routes("create_prompt_note")
    @Serializable
    object CreateNotePromptCompleted : Routes("create_prompt_note_completed")
    @Serializable
    object CreateNote : Routes("create_note")

    @Serializable
    object Statistic : Routes("statistic")
    @Serializable
    object Profile : Routes("profile")

    @Serializable
    object SosGraph : Routes("sos")
    @Serializable
    object SosProfessional : Routes("sos_professional")

    @Serializable
    object Meditation : Routes("meditation")
    @Serializable
    object MeditationMusic : Routes("meditation_music")
    @Serializable
    object MeditationCompleted : Routes("meditation_completed")

    @Serializable
    object Senses : Routes("senses")
    @Serializable
    object SensesCompleted : Routes("senses_completed")

    @Serializable
    object Article : Routes("article")
    @Serializable
    object ArticleList : Routes("article_list")
    @Serializable
    data class ArticleDetail(val articleId: String) : Routes("article_detail/{articleId}") {
        companion object {
            fun createRoute(articleId: String) = "article_detail/$articleId"
        }
    }
}