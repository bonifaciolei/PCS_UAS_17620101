package com.example.boni_football_score_viewer.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


data class MatchModel (
        @Json(name = "events") val events: List<Events>
)
@Parcelize
data class Events (
        val id : Long?,
        @Json(name = "idEvent") val idEvent : String?,
        @Json(name = "strHomeTeam") val strHomeTeam : String?,
        @Json(name = "strAwayTeam") val strAwayTeam : String?,
        @Json(name = "intHomeScore") val intHomeScore : String?,
        @Json(name = "intAwayScore")val intAwayScore : String?,
        @Json(name = "dateEvent") val dateEvent : String?,
        var strTeamHomeBadge : String?,
        var strTeamAwayBadge : String?,
        @Json(name = "strVenue") val strVenue : String?,
        @Json(name = "strCountry") val strCountry : String?

) : Parcelable {
    companion object {
        const val TABLE_SAVED_MATCH: String = "TABLE_SAVED_MATCH"
        const val ID: String = "ID_"
        const val ID_EVENT : String = "ID_EVENT"
        const val HOME_TEAM : String = "HOME_TEAM"
        const val AWAY_TEAM : String = "AWAY_TEAM"
        const val HOME_SCORE : String = "HOME_SCORE"
        const val AWAY_SCORE : String = "AWAY_SCORE"
        const val DATE_EVENT : String = "DATE_EVENT"
        const val HOME_BADGE : String = "HOME_BADGE"
        const val AWAY_BADGE : String = "AWAY_BADGE"
        const val STADIUM : String = "STADIUM"
        const val STADIUM_COUNTRY : String = "STADIUM_COUNTRY"
    }
}