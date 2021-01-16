package com.example.boni_football_score_viewer.db

import com.example.boni_football_score_viewer.DetailMatch
import com.example.boni_football_score_viewer.module.DetailMatchModule
import com.example.boni_football_score_viewer.MainMatchFragment
import com.example.boni_football_score_viewer.module.MainMatchModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {

    @ContributesAndroidInjector(modules = arrayOf(MainMatchModule::class))
    abstract fun bindFootballFragment() : MainMatchFragment

    @ContributesAndroidInjector(modules = arrayOf(DetailMatchModule::class))
    abstract fun bindDetailFragment() : DetailMatch

}