package com.example.hellokotlin.di

import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.DataRepositoryImpl
import com.example.hellokotlin.data.util.ImageUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


/**
 *
 * Created by Davide Parise on 08/09/21.
 */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDataRepository():DataRepository{
        return DataRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideImageUtil(dataRepository: DataRepository):ImageUtil{
        return ImageUtil(dataRepository)
    }
}