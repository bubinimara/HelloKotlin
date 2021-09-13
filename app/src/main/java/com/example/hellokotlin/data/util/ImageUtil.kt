package com.example.hellokotlin.data.util

import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.data.network.model.ConfigurationResponse
import java.io.IOException
import javax.inject.Inject


/**
 *
 * Created by Davide Parise on 10/09/21.
 * @Todo:call initialize when app open. Add a load screen and leave others calls without suspend
 * @Todo: change to singleton  ImageUtil.get()
 */
class ImageUtil @Inject constructor(val respository: DataRepository) {
    companion object{
        var config:ConfigurationResponse ?= null
    }

    suspend fun getImageUrlForMovie(movie: Movie): String {
        initialize()
        val baseUrl = config!!.images.base_url.replace("http://", "https://")
        return baseUrl+getImagePosterSize()+movie.poster_path
    }

    suspend fun getImageUrlForUser(user: User): String {
        initialize()
        val baseUrl = config!!.images.base_url.replace("http://", "https://")
        return baseUrl+getImageProfileSize()+user.profile_path
    }

    private fun getImageProfileSize(): String {
        val img = config!!.images;
        return img.profile_sizes[img.profile_sizes.size-1]
    }

    private fun getImagePosterSize(): String {
        val img = config!!.images;
        return img.poster_sizes[img.poster_sizes.size-1]
    }

    private suspend fun initialize() {
        if(config == null) {
            config = respository.configuration().data
                ?: throw IOException("Can't retrieve configuration from server side")
        }

    }
}