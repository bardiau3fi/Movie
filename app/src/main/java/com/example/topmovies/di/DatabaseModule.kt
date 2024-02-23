package com.example.topmovies.di

import android.content.Context
import androidx.room.Room
import com.example.topmovies.db.MovieEntity
import com.example.topmovies.db.MoviesDatabase
import com.example.topmovies.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, MoviesDatabase::class.java, Constants.MOVIES_DATABASE
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: MoviesDatabase) = db.movieDao()

    @Provides
    @Singleton
    fun provideEntity() = MovieEntity()
}