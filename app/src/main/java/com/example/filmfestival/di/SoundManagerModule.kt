package com.example.filmfestival.di

import android.app.Application
import com.example.filmfestival.utils.SoundManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SoundManagerModule {
    @Singleton
    @Provides
    fun provideSoundManager(app: Application) = SoundManager(app)
}