package com.matches.data.network

import com.matches.domain.MatchModel
import com.mhmd.core.domain.ApiResponse

interface MatchesService {

    suspend fun getMatches(): ApiResponse<List<MatchModel>>

}