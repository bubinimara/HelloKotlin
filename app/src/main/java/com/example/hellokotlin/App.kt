package com.example.hellokotlin

import android.app.Application
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.network.ImageLoader
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


/**
 *
 * Created by Davide Parise on 08/09/21.
 */
@HiltAndroidApp
open class App:Application(){

    @Inject
    lateinit var repository: DataRepository

    override fun onCreate() {
        super.onCreate()
        ImageLoader.initialize(repository)
    }
}