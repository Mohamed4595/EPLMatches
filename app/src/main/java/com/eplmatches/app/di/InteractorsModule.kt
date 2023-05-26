package com.eplmatches.app.di

import com.matches.data.cach.MatchDao
import com.matches.data.network.MatchesService
import com.matches.interactors.DeleteMatch
import com.matches.interactors.GetLocalMatches
import com.matches.interactors.GetMatches
import com.matches.interactors.InsertMatch
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideMatches(
        service: MatchesService
    ): GetMatches {
        return GetMatches(
            service = service
        )
    }

    @ViewModelScoped
    @Provides
    fun provideInsertMatch(
        service: MatchDao
    ): InsertMatch {
        return InsertMatch(
            service = service
        )
    }
    @ViewModelScoped
    @Provides
    fun provideFindMatch(
        service: MatchDao
    ): GetLocalMatches {
        return GetLocalMatches(
            service = service
        )
    }

    @ViewModelScoped
    @Provides
    fun provideDeleteMatch(
        service: MatchDao
    ): DeleteMatch {
        return DeleteMatch(
            service = service
        )
    }


}
