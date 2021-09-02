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
    class Success<T>(data: T?):Resource<T>(data=data){}
    class Loading<T>(data: T?,error: Int?):Resource<T>(data,error){}
    class Error<T>(error: Int?):Resource<T>(error = error){
        object ErrorCode{
            var UNKNOWN_ERROR = -1
        }
    }

}





