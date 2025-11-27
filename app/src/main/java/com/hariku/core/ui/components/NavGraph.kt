package com.hariku.core.ui.components

import PinScreenFull
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hariku.feature_article.presentation.ArticleDetailScreen
import com.hariku.feature_article.presentation.ArticleListScreen
import com.hariku.feature_article.presentation.ArticleScreen
import com.hariku.feature_auth.presentation.login.LoginScreen
import com.hariku.feature_auth.presentation.register.RegisterScreen
import com.hariku.feature_chatbot.presentation.ChatbotScreen
import com.hariku.feature_chatbot.presentation.customize.CustomizeCatScreen
import com.hariku.feature_chatbot.presentation.customize.CustomizeNewCatScreen
import com.hariku.feature_chatbot.presentation.customize.CustomizePersonalCatScreen
import com.hariku.feature_chatbot.presentation.detail.ChatbotDetailScreen
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
import com.hariku.feature_statistic.presentation.StatisticScreen

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
        composable(Routes.Chatbot.route) { ChatbotScreen(navController) }
        composable("detail_chatbot/{chatbotId}") { backStackEntry ->
            val chatbotId = backStackEntry.arguments?.getString("chatbotId") ?: ""
            ChatbotDetailScreen(navController, chatbotId)
        }
        composable(Routes.CustomizeCat.route) { CustomizeCatScreen(navController) }
        composable(Routes.CustomizeNewCat.route) { CustomizeNewCatScreen(navController) }
        composable("customize_personal_cat/{name}/{avatarResId}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val avatarResId = backStackEntry.arguments?.getString("avatarResId")?.toIntOrNull() ?: 0
            CustomizePersonalCatScreen(navController, name, avatarResId)
        }
        composable(Routes.Journal.route) { JournalScreen(navController) }
        composable("journal_detail/{journalId}") { backStackEntry ->
            val journalId = backStackEntry.arguments?.getString("journalId") ?: ""
            JournalDetailScreen(navController, journalId)
        }
        composable(Routes.CreateJournal.route) { CreateJournalScreen() }
        composable(Routes.CreateNotePrompt.route) { CreateNotePromptScreen(navController) }
        composable(Routes.CreateNotePromptCompleted.route) { CreateNotePromptDoneScreen(navController) }
        composable(Routes.CreateNote.route) { CreateNoteScreen(navController) }
        composable(Routes.Statistic.route) { StatisticScreen() }
        composable(Routes.Profile.route) { ProfileScreen(navController) }
        composable(Routes.SosGraph.route) { SosScreen(navController) }
        composable(Routes.SosProfessional.route) { SosProfessionalScreen(navController) }
        composable(Routes.Senses.route) { SensesScreen(navController) }
        composable(Routes.Meditation.route) {
            MeditationScreen(
                onNavigateToSong = { songId ->
                    navController.navigate(Routes.MeditationMusic.createRoute(songId))
                }
            )
        }
        composable("meditation_music/{songId}") { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId") ?: ""
            MeditationSongScreen(
                songId = songId,
                onNavigateToCompleted = { completedSongId ->
                    navController.navigate(Routes.MeditationCompleted.createRoute(completedSongId)) {
                        popUpTo("meditation_music/$songId") { inclusive = true }
                    }
                },
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable("meditation_completed/{songId}") { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId") ?: ""
            MeditationSongCompletedScreen(
                songId = songId,
                onReturnToMeditationClick = {
                    navController.navigate(Routes.Meditation.route) {
                        popUpTo(Routes.Meditation.route) { inclusive = true }
                    }
                },
                onRepeatClick = { repeatSongId ->
                    navController.navigate(Routes.MeditationMusic.createRoute(repeatSongId)) {
                        popUpTo("meditation_completed/$songId") { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.Senses.route) { SensesScreen(navController) }
        composable(Routes.Article.route) {
            ArticleScreen(
                onArticleClick = { articleId ->
                    navController.navigate("article_detail/$articleId")
                },
                onCategoryClick = { category ->
                    navController.navigate("article_list/$category")
                }
            )
        }
        composable("article_list/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            ArticleListScreen(
                category = category,
                onBackClick = { navController.navigateUp() },
                onArticleClick = { articleId ->
                    navController.navigate("article_detail/$articleId")
                }
            )
        }
        composable("article_detail/{articleId}") { backStackEntry ->
            val articleId = backStackEntry.arguments?.getString("articleId") ?: ""
            ArticleDetailScreen(
                articleId = articleId,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}