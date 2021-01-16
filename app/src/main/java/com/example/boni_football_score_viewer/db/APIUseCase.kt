package com.example.boni_football_score_viewer.db

import com.example.boni_football_score_viewer.db.ApiService

interface APIUseCase {
    fun getFootBallResponse() : ApiService
}