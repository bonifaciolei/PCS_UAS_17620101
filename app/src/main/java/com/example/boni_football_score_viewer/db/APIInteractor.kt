package com.example.boni_football_score_viewer.db

import javax.inject.Inject

class APIInteractor @Inject constructor(private val loadAPIRepository: LoadAPIRepository) :
        APIUseCase {
    override fun getFootBallResponse(): ApiService {
        return loadAPIRepository.execute()
    }

}