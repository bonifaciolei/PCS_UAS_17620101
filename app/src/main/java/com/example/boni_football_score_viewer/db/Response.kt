package com.example.boni_football_score_viewer.db

sealed class Response {
    object Loading : Response()
    data class Success (val data: Any?) : Response()
    object Error : Response()
}