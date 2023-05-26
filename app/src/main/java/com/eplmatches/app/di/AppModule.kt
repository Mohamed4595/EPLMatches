package com.eplmatches.app.di

import android.content.Context
import com.eplmatches.app.ui.BaseApplication
import com.mhmd.core.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }
    @Provides
    @Singleton
    fun provideLogger(): Logger {
        return Logger(
            tag = "AppDebug",
            isDebug = true
        )
    }
}