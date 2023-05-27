package com.eplmatches.app.di

import android.content.Context
import androidx.room.Room
import com.eplmatches.app.appDatabase.AppDatabase
import com.eplmatches.app.ui.BaseApplication
import com.matches.data.cach.MatchDao
import com.mhmd.core.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }




}