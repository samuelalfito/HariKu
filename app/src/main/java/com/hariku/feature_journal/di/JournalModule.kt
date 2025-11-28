package com.hariku.feature_journal.di

import androidx.room.Room
import com.google.ai.client.generativeai.GenerativeModel
import com.hariku.feature_journal.data.local.JournalDatabase
import com.hariku.feature_journal.data.mapper.NoteMapper
import com.hariku.feature_journal.data.remote.JournalRemoteDataSource
import com.hariku.feature_journal.data.remote.NoteRemoteDataSource
import com.hariku.feature_journal.data.remote.PromptAIService
import com.hariku.feature_journal.data.repository.JournalRepositoryImpl
import com.hariku.feature_journal.data.repository.NoteRepositoryImpl
import com.hariku.feature_journal.data.repository.PromptRepositoryImpl
import com.hariku.feature_journal.domain.repository.JournalRepository
import com.hariku.feature_journal.domain.repository.NoteRepository
import com.hariku.feature_journal.domain.repository.PromptRepository
import com.hariku.feature_journal.domain.usecase.*
import com.hariku.feature_journal.presentation.JournalViewModel
import com.hariku.feature_journal.presentation.create.journal.CreateJournalViewModel
import com.hariku.feature_journal.presentation.create.note.CreateNoteViewModel
import com.hariku.feature_journal.presentation.create.prompt.CreateNotePromptViewModel
import com.hariku.feature_journal.presentation.create.prompt.CreateNotePromptCompletedViewModel
import com.hariku.feature_journal.presentation.create.prompt.SharedPromptViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val journalModule = module {
    
    single {
        Room.databaseBuilder(
            androidContext(),
            JournalDatabase::class.java,
            JournalDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration(true).build()
    }
    single { get<JournalDatabase>().journalDao() }
    single { get<JournalDatabase>().promptDao() }
    single { get<JournalDatabase>().noteDao() }
    single { get<JournalDatabase>().journalOverviewDao() }
    
    // Mappers
    single { NoteMapper() }
    single { com.hariku.feature_journal.data.mapper.JournalOverviewMapper() }
    
    // Remote Data Sources
    single { JournalRemoteDataSource(firestore = get(), auth = get()) }
    single { NoteRemoteDataSource(firestore = get()) }
    single { com.hariku.feature_journal.data.remote.JournalOverviewRemoteDataSource(firestore = get(), generativeModel = get()) }
    
    // Repositories
    single<JournalRepository> {
        JournalRepositoryImpl(
            remoteDataSource = get()
        )
    }
    
    single<NoteRepository> {
        NoteRepositoryImpl(
            remoteDataSource = get(),
            noteDao = get(),
            mapper = get()
        )
    }
    
    single<com.hariku.feature_journal.domain.repository.JournalOverviewRepository> {
        com.hariku.feature_journal.data.repository.JournalOverviewRepositoryImpl(
            remoteDataSource = get(),
            dao = get(),
            mapper = get()
        )
    }
    
    single<PromptRepository> {
        PromptRepositoryImpl(
            aiService = get(),
            dao = get()
        )
    }
    
    // Journal Use Cases
    single { GetAllJournalsUseCase(repository = get()) }
    single { SaveJournalUseCase(repository = get()) }
    single { DeleteJournalUseCase(repository = get()) }
    
    // Note Use Cases
    single { SaveNoteUseCase(repository = get()) }
    single { GetNotesByJournalUseCase(repository = get()) }
    
    // Journal Overview Use Cases
    single { com.hariku.feature_journal.domain.usecase.GenerateJournalOverviewUseCase(repository = get()) }
    single { com.hariku.feature_journal.domain.usecase.GetJournalOverviewUseCase(repository = get()) }
    
    single {
        JournalUseCases(
            getAllJournals = get(),
            saveJournal = get(),
            deleteJournal = get()
        )
    }
    
    // Prompt Use Cases
    single { GeneratePromptsUseCase(repository = get()) }
    
    single {
        GenerativeModel(
            modelName = "gemini-2.0-flash-lite",
            apiKey = com.hariku.BuildConfig.GEMINI_API_KEY
        )
    }
    single { PromptAIService(generativeModel = get()) }
    
    viewModel { JournalViewModel(useCases = get()) }
    viewModel {
        CreateJournalViewModel(
            useCases = get(),
            getCurrentUserUseCase = get()
        )
    }
    viewModel {
        CreateNoteViewModel(
            saveNoteUseCase = get(),
            getCurrentUserUseCase = get()
        )
    }
    viewModel {
        com.hariku.feature_journal.presentation.detail.JournalDetailViewModel(
            getNotesByJournalUseCase = get(),
            getJournalOverviewUseCase = get(),
            generateJournalOverviewUseCase = get()
        )
    }
    viewModel {
        CreateNotePromptViewModel(
            generatePromptsUseCase = get(),
            sharedViewModel = get()
        )
    }
    viewModel {
        CreateNotePromptCompletedViewModel(sharedViewModel = get())
    }
    single { SharedPromptViewModel() }
}