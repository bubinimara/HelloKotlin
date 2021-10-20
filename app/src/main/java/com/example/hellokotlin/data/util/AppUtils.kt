package com.example.hellokotlin.data.util

import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.data.network.model.ConfigurationResponse
import kotlinx.coroutines.flow.collect
import java.io.IOException
import javax.inject.Inject


/**
 *
 * Created by Davide Parise on 10/09/21.
 * @Todo: change to singleton  ImageUtil.get()
 */
class AppUtils @Inject constructor(val respository: DataRepository) {
    object ImageUtils{
        var config:ConfigurationResponse ?= null

        fun getImageUrlForMovie(movie: Movie): String {
            val baseUrl = config!!.images.base_url.replace("http://", "https://")
            return baseUrl+getImagePosterSize()+movie.poster_path
        }

        fun getImageUrlForUser(user: User): String {
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
    }


    suspend fun initialize() {
        if(ImageUtils.config == null) {
            respository.configuration().collect {
                when(it){
                    is Resource.Error -> throw IOException("Error while retrieve configuration from server side")
                    is Resource.Loading -> {}// do nothing
                    is Resource.Success -> {   ImageUtils.config = it.data
                        ?: throw IOException("Can't retrieve configuration from server side")
                    }
                }
            }
        }

    }
}