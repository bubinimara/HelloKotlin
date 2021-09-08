package com.example.hellokotlin.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 *
 * Created by Davide Parise on 08/09/21.
 * All Application Level dependencies
 */
@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
}