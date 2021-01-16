package com.example.boni_football_score_viewer.module

import com.example.boni_football_score_viewer.db.APIInteractor
import com.example.boni_football_score_viewer.model.MainMatchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MainMatchModule {

    @Provides
    fun providePrevMatchViewModelFactory(APIInteractor: APIInteractor) : MainMatchViewModelFactory {
        return MainMatchViewModelFactory(APIInteractor)
    }
}