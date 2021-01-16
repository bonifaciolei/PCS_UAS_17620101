package com.example.boni_football_score_viewer.model

import com.squareup.moshi.Json

data class TeamModel (
        @Json(name = "teams") val teams : List<TeamNameModel>
)

data class TeamNameModel (
        @Json(name = "strTeamBadge") val strTeamBadge : String
)