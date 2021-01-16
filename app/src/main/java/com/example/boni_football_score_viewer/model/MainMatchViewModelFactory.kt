package com.example.boni_football_score_viewer.model

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.boni_football_score_viewer.db.APIInteractor

class MainMatchViewModelFactory (
        val apiInteractor: APIInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(MainMatchViewModel::class.java) -> {
                return MainMatchViewModel(apiInteractor) as (T)
            }
        }
        throw IllegalArgumentException("")
    }

}