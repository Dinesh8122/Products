package com.example.productlist.database.remote

import android.util.Log

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            Log.i("Resource", "error: $message")

            if(message.contains("timeout")){
                return Resource(Status.ERROR, data, "Connection timed out. Please try again")
            }

            if(message.contains("Unable to resolve host")){
                return Resource(Status.ERROR, data, "Please check your internet connection and try again")
            }

            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}