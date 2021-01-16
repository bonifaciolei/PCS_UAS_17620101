package com.example.boni_football_score_viewer.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.boni_football_score_viewer.UPCOMING_MATCH
import com.example.boni_football_score_viewer.RESULT_MATCH
import com.example.boni_football_score_viewer.db.Response
import com.example.boni_football_score_viewer.db.APIInteractor
import com.example.boni_football_score_viewer.db.APIUseCase
import com.example.boni_football_score_viewer.db.database
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import retrofit2.HttpException
import javax.inject.Inject


const val ENGLISH_LEAGUE = "4328"

class MainMatchViewModel @Inject constructor(
        val footBallInteractor: APIInteractor
) : ViewModel(){

    private val response = MutableLiveData<Response>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun response () : MutableLiveData<Response> = response

    suspend fun loadDataFootball(menu : String, context: Context?) = loadFootballEvent(footBallInteractor, menu, context)

    suspend fun loadFootballEvent(APIUseCase: APIUseCase, menu : String, context: Context?){

        when(menu) {
            RESULT_MATCH -> {
                try{
                    val responsAPI = APIUseCase
                            .getFootBallResponse()
                            .getFootballEventPrev(ENGLISH_LEAGUE)
                    response.value = Response.Success(responsAPI.await())
                } catch (e : HttpException){
                    response.value = Response.Error
                } catch (e : Throwable){
                    response.value = Response.Error
                }
//
            }
            UPCOMING_MATCH -> {
                try{
                    val responsAPI = APIUseCase
                            .getFootBallResponse()
                            .getFootBallEventNext(ENGLISH_LEAGUE)
                    response.value = Response.Success(responsAPI.await())
                } catch (e : HttpException){
                    response.value = Response.Error
                } catch (e : Throwable){
                    response.value = Response.Error
                }

            }
            else -> {
                context?.database?.use {
                    val result = select(Events.TABLE_SAVED_MATCH)
                    val favorite = result.parseList(classParser<Events>())

                    response.value = Response.Success(favorite)
                }
            }
        }


    }

}