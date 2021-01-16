package com.example.boni_football_score_viewer.db

import javax.inject.Inject

class LoadAPI @Inject constructor(private val apiService: ApiService) :
        LoadAPIRepository {
    override fun execute(): ApiService {
        return apiService
    }

}