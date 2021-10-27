package com.example.hellokotlin.data.network

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.hellokotlin.data.DataRepository
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.network.model.ConfigurationResponse
import kotlinx.coroutines.*


/**
 *
 * Created by Davide Parise on 26/10/21.
 */
class ImageLoader{
    companion object{
        private var _repository:DataRepository? = null
        private var configurationResponse:ConfigurationResponse? = null

        fun initialize(repository: DataRepository){
            _repository = repository
        }

        fun load(movie: Movie,image:ImageView) {
            val repository = _repository ?: throw Exception("Must call init() first")

            val job = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + job+CoroutineName("ImageLoader"))

        /*    image.doOnDetach {
                job.cancel("Image detached from window")
            }*/

            uiScope.launch(Dispatchers.Main){
                if(configurationResponse == null) {
                    configurationResponse = withContext(Dispatchers.IO) {
                        repository.configuration()
                    }
                }

                val url = ImageUtils.getImageUrlForMovie(configurationResponse!!,movie)
                // set size too
                Glide.with(image)
                    .load(url)
                    .centerCrop()
                    .into(image)
            }
        }
    }

    private object ImageUtils{
        fun getImageUrlForMovie(configurationResponse: ConfigurationResponse,movie: Movie): String {
            val baseUrl = getBaseUrlFromConfiguration(configurationResponse)
            return baseUrl+getImagePosterSize(configurationResponse)+movie.poster_path
        }

        private fun getBaseUrlFromConfiguration(configurationResponse: ConfigurationResponse):String{
            return configurationResponse.images.base_url.replace("http://", "https://")
        }
        private fun getImagePosterSize(configurationResponse: ConfigurationResponse): String {
            val img = configurationResponse.images;
            return img.poster_sizes[img.poster_sizes.size-1]
        }
    }
}