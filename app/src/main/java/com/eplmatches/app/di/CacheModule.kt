package com.eplmatches.app.di

import androidx.room.Room
import com.eplmatches.app.appDatabase.AppDatabase
import com.eplmatches.app.ui.BaseApplication
import com.matches.data.cach.MatchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMatchesDao(db: AppDatabase): MatchDao {
        return db.matchDao()
    }
}







