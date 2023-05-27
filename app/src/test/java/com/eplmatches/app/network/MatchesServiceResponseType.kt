package com.eplmatches.app.network

sealed class MatchesServiceResponseType{

    object EmptyList: MatchesServiceResponseType()

    object MalformedData: MatchesServiceResponseType()

    object GoodData: MatchesServiceResponseType()

    object Http404: MatchesServiceResponseType()
}
