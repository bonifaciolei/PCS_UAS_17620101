package com.example.boni_football_score_viewer.module

import com.example.boni_football_score_viewer.db.APIInteractor
import com.example.boni_football_score_viewer.model.DetailMatchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class DetailMatchModule {

    @Provides
    fun provideDetailMatchViewModelFactory
            (footballInteractor: APIInteractor) : DetailMatchViewModelFactory {
        return DetailMatchViewModelFactory(footballInteractor)
    }
}