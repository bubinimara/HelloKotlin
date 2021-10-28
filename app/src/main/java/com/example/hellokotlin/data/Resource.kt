package com.example.hellokotlin.data

/**
 * @author Davide Parise
 *
 * Default class the wrap a requests resource
 * @param data The data
 * @param error if an error occurs
 *
 */
sealed class Resource<T>(
    var data: T? =null,
    var error: Int? = null
){
    class Success<T>(data: T?):Resource<T>(data=data)
    class Loading<T>(data: T? = null):Resource<T>(data = data)
    class Error<T>(error: Int = ErrorCode.UNKNOWN_ERROR):Resource<T>(error = error){
        object ErrorCode{
            val INVALID_USERNAME_OR_PASSWORD = -4
            val NETWORK_ERROR = -3
            val INVALID_TOKEN = -2
            var UNKNOWN_ERROR = -1
        }
    }

}





