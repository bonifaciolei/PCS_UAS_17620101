package com.example.boni_football_score_viewer.db


interface LoadAPIRepository {
    fun execute () : ApiService
}