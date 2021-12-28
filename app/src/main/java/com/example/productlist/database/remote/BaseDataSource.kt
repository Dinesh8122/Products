package com.example.productlist.database.remote

import android.util.Log
import com.example.productlist.database.entities.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response
import java.security.cert.CertPathValidatorException



abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }

            val gson = Gson()

            val errorResponse =
                gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)

            Log.i("BaseDataSource", "getResult: Error code: ${response.code()}")
            Log.i("BaseDataSource", "getResult : Error: $errorResponse")

            if (errorResponse.error != null) {
                return error(errorResponse.error)
            } else {
                return error("Unknown error, Please contact support.")
            }
        } catch (e: Exception) {
            Log.i("BaseDataSource", "Exception -: $e")
            return error(e.message ?: e.toString())
        } catch (e: CertPathValidatorException) {
            Log.i("BaseDataSource", "CertPathValidatorException -: $e")
            return error("Unable to connect to server. Please try again.")
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error(message)
    }

}