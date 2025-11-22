package com.hariku.core.ui.components

import ChatScreen
import PinScreenFull
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hariku.feature_article.presentation.ArticleDetailScreen
import com.hariku.feature_article.presentation.ArticleScreen
import com.hariku.feature_article.presentation.KecemaasanScreen
import com.hariku.feature_auth.presentation.login.LoginScreen
import com.hariku.feature_auth.presentation.register.RegisterScreen
import com.hariku.feature_chatbot.presentation.customize.CustomizeCatScreen
import com.hariku.feature_chatbot.presentation.customize.CustomizeNewCatScreen
import com.hariku.feature_chatbot.presentation.customize.CustomizePersonalCatScreen
import com.hariku.feature_chatbot.presentation.detail.ChatDetailScreen
import com.hariku.feature_journal.presentation.JournalScreen
import com.hariku.feature_journal.presentation.create.journal.CreateJournalScreen
import com.hariku.feature_journal.presentation.create.note.CreateNoteScreen
import com.hariku.feature_journal.presentation.create.prompt.CreateNotePromptDoneScreen
import com.hariku.feature_journal.presentation.create.prompt.CreateNotePromptScreen
import com.hariku.feature_journal.presentation.detail.JournalDetailScreen
import com.hariku.feature_meditation.presentation.MeditationScreen
import com.hariku.feature_meditation.presentation.MeditationSongCompletedScreen
import com.hariku.feature_meditation.presentation.MeditationSongScreen
import com.hariku.feature_onboarding.presentation.OnboardingScreen
import com.hariku.feature_onboarding.presentation.SplashScreen
import com.hariku.feature_pin.presentation.FillPinScreen
import com.hariku.feature_profile.presentation.ProfileScreen
import com.hariku.feature_sense.presentation.SensesScreen
import com.hariku.feature_sos.presentation.SosProfessionalScreen
import com.hariku.feature_sos.presentation.SosScreen
import com.hariku.feature_statistic.presentation.components.CalendarView

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Splash.route) {
        composable(Routes.Splash.route) { SplashScreen(navController) }
        composable(Routes.Onboarding.route) { OnboardingScreen(navController) }
        navigation(Routes.Login.route, Routes.AuthGraph.route) {
            composable(Routes.Login.route) { LoginScreen(navController) }
            composable(Routes.Register.route) { RegisterScreen(navController) }
        }
        navigation(Routes.TetapkanPin.route, Routes.PinGraph.route) {
            composable(Routes.TetapkanPin.route) { PinScreenFull(navController) }
            composable(Routes.MasukkanPin.route) { FillPinScreen(navController) }
        }
        composable(Routes.Home.route) { MainScaffold(parentNavController = navController) }
        composable(Routes.Chatbot.route) { ChatScreen(navController) }
        composable("detail_chatbot/{chatbotId}") { backStackEntry ->
            val chatbotId = backStackEntry.arguments?.getString("chatbotId") ?: ""
            ChatDetailScreen(navController, chatbotId)
        }
        composable(Routes.CustomizeCat.route) { CustomizeCatScreen(navController) }
        composable(Routes.CustomizeNewCat.route) { CustomizeNewCatScreen(navController) }
        composable(Routes.CustomizePersonalCat.route) { CustomizePersonalCatScreen(navController) }
        composable(Routes.Journal.route) { JournalScreen(navController) }
        composable("journal_detail/{journalId}") { backStackEntry ->
            val journalId = backStackEntry.arguments?.getString("journalId") ?: ""
            JournalDetailScreen(navController, journalId)
        }
        composable(Routes.CreateJournal.route) { CreateJournalScreen() }
        composable(Routes.CreateNotePrompt.route) { CreateNotePromptScreen(navController) }
        composable(Routes.CreateNotePromptCompleted.route) { CreateNotePromptDoneScreen(navController) }
        composable(Routes.CreateNote.route) { CreateNoteScreen(navController) }
        composable(Routes.Statistic.route) { CalendarView() }
        composable(Routes.Profile.route) { ProfileScreen(navController) }
        composable(Routes.SosGraph.route) { SosScreen(navController) }
        composable(Routes.SosProfessional.route) { SosProfessionalScreen(navController) }
        composable(Routes.Meditation.route) { MeditationScreen() }
        composable(Routes.MeditationMusic.route) { MeditationSongScreen() }
        composable(Routes.MeditationCompleted.route) { MeditationSongCompletedScreen() }
        composable(Routes.Senses.route) { SensesScreen(navController) }
        composable(Routes.Article.route) { ArticleScreen() }
        composable(Routes.ArticleList.route) { KecemaasanScreen() }
        composable("article_detail/{articleId}") { backStackEntry ->
            val articleId = backStackEntry.arguments?.getString("articleId") ?: ""
            ArticleDetailScreen(articleIndex = articleId.toInt())
        }
    }
}