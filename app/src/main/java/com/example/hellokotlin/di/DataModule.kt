package com.example.hellokotlin.di

import android.content.Context
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.DataRepositoryImpl
import com.example.hellokotlin.data.db.AppDb
import com.example.hellokotlin.data.session.SessionManager
import com.example.hellokotlin.data.session.SessionManagerImpl
import com.example.hellokotlin.data.network.NetworkServices
import com.example.hellokotlin.data.network.cache.CacheManager
import com.example.hellokotlin.data.util.AppUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideSessionManager(@ApplicationContext context: Context):SessionManager{
        return SessionManagerImpl(context)
    }

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkServices{
        return NetworkServices()
    }

    @Provides
    @Singleton
    fun provideCacheManger(@ApplicationContext context: Context,appDb: AppDb): CacheManager {
        return CacheManager.getInstance(context,appDb)
    }
    @Provides
    @Singleton
    fun provideDataRepository(networkServices: NetworkServices, sessionManager: SessionManager,cacheManager: CacheManager):DataRepository{
        return DataRepositoryImpl(networkServices.apiService,sessionManager,cacheManager)
    }

    @Provides
    @Singleton
    fun provideImageUtil(dataRepository: DataRepository):AppUtils{
        return AppUtils(dataRepository)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):AppDb{
        return AppDb.getDb(context)
    }

}