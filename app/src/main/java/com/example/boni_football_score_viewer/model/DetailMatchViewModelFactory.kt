package com.example.boni_football_score_viewer.model

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.boni_football_score_viewer.db.APIInteractor

class DetailMatchViewModelFactory (
        val footballInteractor: APIInteractor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(DetailMatchViewModel::class.java) -> {
                return DetailMatchViewModel(footballInteractor) as (T)
            }
        }
        throw IllegalArgumentException(" Kelas tidak diketahui")
    }

}