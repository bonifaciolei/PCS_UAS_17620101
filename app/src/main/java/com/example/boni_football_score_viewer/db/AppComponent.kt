package com.example.boni_football_score_viewer.db

import android.app.Application
import com.example.boni_football_score_viewer.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton

@Component(modules = [NetworkModule::class,
    APIInteractorModule::class,
    BuilderModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent  {


    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app: Application) : Builder
        fun build() : AppComponent
    }

    fun inject(app: App)

}