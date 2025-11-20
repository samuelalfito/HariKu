package com.hariku.feature_journal.di

import androidx.room.Room
import com.hariku.feature_journal.data.local.JournalDatabase
import com.hariku.feature_journal.data.mapper.JournalMapper
import com.hariku.feature_journal.data.repository.JournalRepositoryImpl
import com.hariku.feature_journal.domain.repository.JournalRepository
import com.hariku.feature_journal.domain.usecase.*
import com.hariku.feature_journal.presentation.JournalScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Modul Koin untuk Journal Feature.
 */
val journalModule = module {

    // --- Database/DAO ---
    single {
        Room.databaseBuilder(
            androidContext(),
            JournalDatabase::class.java,
            JournalDatabase.DATABASE_NAME
        ).build()
    }
    single { get<JournalDatabase>().journalDao() }

    // --- Mapper ---
    single { JournalMapper() }

    // --- Repository ---
    single<JournalRepository> {
        JournalRepositoryImpl(dao = get(), mapper = get())
    }

    // --- Use Cases ---
    single { GetAllJournalsUseCase(repository = get()) }
    single { GetJournalByIdUseCase(repository = get()) }
    single { SaveJournalUseCase(repository = get()) }
    single { DeleteJournalUseCase(repository = get()) }

    // Gabungkan Use Cases dalam satu objek untuk injeksi yang bersih
    single {
        JournalUseCases(
            getAllJournals = get(),
            getJournalById = get(),
            saveJournal = get(),
            deleteJournal = get()
        )
    }

    // --- ViewModel ---
     viewModel{
        JournalScreenViewModel(
            useCases = get() // Menggunakan objek JournalUseCases
        )
    }
}