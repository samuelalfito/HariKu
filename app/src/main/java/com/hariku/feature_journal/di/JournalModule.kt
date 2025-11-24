package com.hariku.feature_journal.di

import com.hariku.feature_journal.data.remote.JournalRemoteDataSource
import com.hariku.feature_journal.data.repository.JournalRepositoryImpl
import com.hariku.feature_journal.domain.repository.JournalRepository
import com.hariku.feature_journal.domain.usecase.DeleteJournalUseCase
import com.hariku.feature_journal.domain.usecase.GetAllJournalsUseCase
import com.hariku.feature_journal.domain.usecase.JournalUseCases
import com.hariku.feature_journal.domain.usecase.SaveJournalUseCase
import com.hariku.feature_journal.presentation.JournalViewModel
import com.hariku.feature_journal.presentation.create.journal.CreateJournalViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Modul Koin untuk Journal Feature.
 */
val journalModule = module {

    // --- Database/DAO ---
//    single {
//        Room.databaseBuilder(
//            androidContext(),
//            JournalDatabase::class.java,
//            JournalDatabase.DATABASE_NAME
//        ).build()
//    }
//    single { get<JournalDatabase>().journalDao() }

    // --- Remote Data Source ---
    single { JournalRemoteDataSource(firestore = get(), auth = get()) }

    // --- Mapper ---

    // --- Repository ---
    single<JournalRepository> { JournalRepositoryImpl(remoteDataSource = get()) }

    // --- Use Cases ---
    factory { GetAllJournalsUseCase(repository = get()) }
//    factory { GetJournalByIdUseCase(repository = get()) }
    factory { SaveJournalUseCase(repository = get()) }
    factory { DeleteJournalUseCase(repository = get()) }

    // Gabungkan Use Cases dalam satu objek untuk injeksi yang bersih
    factory {
        JournalUseCases(
            getAllJournals = get(),
//            getJournalById = get(),
            saveJournal = get(),
            deleteJournal = get()
        )
    }

    // --- ViewModel ---
     viewModel{
         JournalViewModel(
             useCases = get() // Menggunakan objek JournalUseCases
         )
    }

    viewModel {
        CreateJournalViewModel(
            useCases = get(),
            getCurrentUserUseCase = get()
        )
    }
}