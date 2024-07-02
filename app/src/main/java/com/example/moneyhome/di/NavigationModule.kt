package com.example.moneyhome.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    @Provides
    fun provideNavigationController(): NavigationController {
        return NavigationControllerImpl()
    }
}

interface NavigationController {
    fun navigateToMenu()
}

class NavigationControllerImpl : NavigationController {
    override fun navigateToMenu() {
        // Implement navigation logic here
    }
}