package com.hariku.feature_article.di

import com.hariku.feature_article.data.remote.ArticleRemoteDataSource
import com.hariku.feature_article.data.repository.ArticleRepositoryImpl
import com.hariku.feature_article.domain.repository.ArticleRepository
import com.hariku.feature_article.domain.usecase.GetArticleByIdUseCase
import com.hariku.feature_article.domain.usecase.GetArticlesUseCase
import com.hariku.feature_article.domain.usecase.GetArticlesByCategoryUseCase
import com.hariku.feature_article.domain.usecase.SearchArticlesUseCase
import com.hariku.feature_article.presentation.ArticleViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val articleModule = module {
    
    single {
        ArticleRemoteDataSource(firestore = get())
    }
    
    single<ArticleRepository> {
        ArticleRepositoryImpl(
            remoteDataSource = get()
        )
    }
    
    factory {
        GetArticlesUseCase(get())
    }
    
    factory {
        GetArticleByIdUseCase(get())
    }
    
    factory {
        GetArticlesByCategoryUseCase(get())
    }
    
    factory {
        SearchArticlesUseCase(get())
    }
    
    viewModel {
        ArticleViewModel(
            getArticlesUseCase = get(),
            getArticleByIdUseCase = get()
        )
    }
}