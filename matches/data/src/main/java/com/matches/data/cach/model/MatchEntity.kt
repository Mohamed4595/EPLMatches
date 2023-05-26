package com.matches.data.cach.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matches.domain.MatchModel
import com.matches.domain.ScoreModel
import com.matches.domain.ScoreTimeModel
import com.matches.domain.TeamModel
import com.mhmd.core.util.convertMillisToZonedDateTime
import com.mhmd.core.util.convertZonedToDateTimeMillis

@Entity(tableName = "matches")
data class MatchEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "utc_date")
    var utcDate: Long,


    //I should put this team information to another table and make relation one to many
    ///////////////////////////////////////////////////////////////////////////////////
    @ColumnInfo(name = "home_id")
    var homeId: Int,

    @ColumnInfo(name = "home_name")
    var homeName: String,

    @ColumnInfo(name = "home_image")
    var homeImage: String,

    @ColumnInfo(name = "away_id")
    var awayId: Int,

    @ColumnInfo(name = "away_name")
    var awayName: String,

    @ColumnInfo(name = "away_image")
    var awayImage: String,

///////////////////////////////////////////////////////////////////////////////////

    @ColumnInfo(name = "full_home_score")
    var fullHomeScore: Int?,

    @ColumnInfo(name = "full_away_score")
    var fullAwayScore: Int?,

    @ColumnInfo(name = "half_home_score")
    var halfHomeScore: Int?,

    @ColumnInfo(name = "half_away_score")
    var halfAwayScore: Int?,


    )

fun MatchModel.toMatchEntity(): MatchEntity {
    val date = date?.convertZonedToDateTimeMillis() ?: 0L
    return MatchEntity(
        id = id.toInt(),
        status = status,
        utcDate = date,
        homeId = homeTeam.teamId.toInt(),
        homeImage = homeTeam.teamImage,
        homeName = homeTeam.teamName,
        awayId = awayTeam.teamId.toInt(),
        awayImage = awayTeam.teamImage,
        awayName = awayTeam.teamName,
        halfHomeScore = score?.halfTime?.home,
        halfAwayScore = score?.halfTime?.away,
        fullHomeScore = score?.fullTime?.home,
        fullAwayScore = score?.fullTime?.away
    )
}

fun MatchEntity.toMatchModel(): MatchModel {
    return MatchModel(
        id = id.toLong(), status = status, date = utcDate.convertMillisToZonedDateTime(),
        homeTeam = TeamModel(teamId = homeId.toLong(), teamName = homeName, teamImage = homeImage),
        awayTeam = TeamModel(teamId = awayId.toLong(), teamName = awayName, teamImage = awayImage),
        score = ScoreModel(
            fullTime = if (fullHomeScore == null && fullAwayScore == null) null else ScoreTimeModel(
                home = fullHomeScore!!,
                away = fullAwayScore!!
            ),
            halfTime = if (halfHomeScore == null && halfAwayScore == null) null else ScoreTimeModel(
                home = halfHomeScore!!,
                away = halfAwayScore!!
            )
        )
    )
}