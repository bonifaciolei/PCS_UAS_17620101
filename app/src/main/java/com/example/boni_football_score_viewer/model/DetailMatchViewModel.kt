package com.example.boni_football_score_viewer.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.boni_football_score_viewer.db.Response
import com.example.boni_football_score_viewer.db.APIInteractor
import com.example.boni_football_score_viewer.db.APIUseCase
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

class DetailMatchViewModel @Inject constructor(
        val footbalInteractor: APIInteractor
) : ViewModel() {
    private val response = MutableLiveData<Response>()

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun response(): MutableLiveData<Response> = response

    suspend fun loadImage(name : String?) = loadImageData(footbalInteractor, name)

    suspend fun loadImageData(APIUsecase : APIUseCase, name : String?){

        try {
            val responseAPI = APIUsecase.getFootBallResponse().getTeamData(name)
            response.value = Response.Success(responseAPI.await())
        } catch (e : HttpException){
            response.value = Response.Error
        } catch (e : Exception){
            response.value = Response.Error
        }
    }

}