package com.hariku.feature_profile.presentation

import androidx.lifecycle.ViewModel
import com.hariku.feature_auth.domain.usecase.GetCurrentUserUseCase
import com.hariku.feature_auth.domain.usecase.LogoutUseCase

class ProfileScreenViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    val currentUser = getCurrentUserUseCase()

    init {
        getCurrentUser()
    }

    fun onLogoutClicked() {
        logoutUseCase()
    }

    fun getCurrentUser(){
        getCurrentUserUseCase()
    }
}