package com.example.boni_football_score_viewer.db

import com.example.boni_football_score_viewer.model.MatchModel
import com.example.boni_football_score_viewer.model.TeamModel
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

const val API_BASE_URL = "https://www.thesportsdb.com/"

interface ApiService {
    @GET("/api/v1/json/1/eventspastleague.php")
    fun getFootballEventPrev(
            @Query("id") id : String?
    ) : Deferred<MatchModel>

    @GET("/api/v1/json/1/searchteams.php")
    fun getTeamData(
            @Query("t") t : String?
    ) : Deferred<TeamModel>

    @GET("/api/v1/json/1/eventsnextleague.php")
    fun getFootBallEventNext(
            @Query("id") id : String?
    ) : Deferred<MatchModel>

}