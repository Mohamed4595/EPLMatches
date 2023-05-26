package com.matches.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TeamDto(
    val id:Long,
    val shortName:String,
    val crest:String,
)
