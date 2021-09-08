package com.example.hellokotlin.di

import android.app.Application
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.DataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


/**
 *
 * Created by Davide Parise on 08/09/21.
 * Provide dependencies from data package
 */
@Module
@InstallIn(ApplicationComponent::class)
class DataModule {

    @Binds
    @Singleton
    fun provideDataRepository():DataRepository{
        return DataRepositoryImpl()
    }
}