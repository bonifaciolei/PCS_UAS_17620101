package com.example.boni_football_score_viewer.db

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class APIInteractorModule {

    @Singleton
    @Provides
    fun provideFootballInteractor(apiService: ApiService) : LoadAPIRepository = LoadAPI(apiService)
}