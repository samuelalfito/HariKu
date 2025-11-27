package com.hariku.feature_journal.di

import androidx.room.Room
import com.google.ai.client.generativeai.GenerativeModel
import com.hariku.feature_journal.data.local.JournalDatabase
import com.hariku.feature_journal.data.mapper.JournalMapper
import com.hariku.feature_journal.data.repository.JournalRepositoryImpl
import com.hariku.feature_journal.data.remote.PromptAIService
import com.hariku.feature_journal.domain.usecase.*
import com.hariku.feature_journal.data.repository.PromptRepositoryImpl
import org.koin.android.ext.koin.androidContext
import com.hariku.feature_journal.domain.repository.PromptRepository
import com.hariku.feature_journal.domain.repository.JournalRepository
import com.hariku.feature_journal.presentation.JournalViewModel
import com.hariku.feature_journal.presentation.create.journal.CreateJournalViewModel
import com.hariku.feature_journal.presentation.create.prompt.CreateNotePromptViewModel
import com.hariku.feature_journal.presentation.create.prompt.CreateNotePromptCompletedViewModel
import com.hariku.feature_journal.presentation.create.prompt.SharedPromptViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val journalModule = module {
    single {
        Room.databaseBuilder(
            androidContext(), JournalDatabase::class.java, JournalDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration(true).build()
    }
    
    single { get<JournalDatabase>().journalDao() }
    single { get<JournalDatabase>().promptDao() }
    
    single { JournalMapper() }
    
    single<JournalRepository> {
        JournalRepositoryImpl(
            dao = get(),
            mapper = get()
        )
    }
    
    single { GetAllJournalsUseCase(repository = get()) }
    single { GetJournalByIdUseCase(repository = get()) }
    single { SaveJournalUseCase(repository = get()) }
    single { DeleteJournalUseCase(repository = get()) }
    
    single {
        JournalUseCases(
            getAllJournals = get(),
            getJournalById = get(),
            saveJournal = get(),
            deleteJournal = get()
        )
    }
    
    single {
        GenerativeModel(
            modelName = "gemini-2.0-flash-lite", apiKey = com.hariku.BuildConfig.GEMINI_API_KEY
        )
    }
    
    single { PromptAIService(generativeModel = get()) }
    
    single<PromptRepository> {
        PromptRepositoryImpl(
            aiService = get(), dao = get()
        )
    }
    
    single { GeneratePromptsUseCase(repository = get()) }
    
    viewModel { JournalViewModel(useCases = get()) }
    viewModel { CreateJournalViewModel() }
    viewModel {
        CreateNotePromptViewModel(
            generatePromptsUseCase = get(), sharedViewModel = get()
        )
    }
    viewModel { CreateNotePromptCompletedViewModel(sharedViewModel = get()) }
    single { SharedPromptViewModel() }
}