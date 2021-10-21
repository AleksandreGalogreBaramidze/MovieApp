package com.example.movieapp.api

data class ApiErrorHandling<T>(val status: Status, val data: T? = null, val message: String? = null, ) {

    enum class Status {
        Success,
        Error,
    }

    companion object {
        fun <T> success(data: T): ApiErrorHandling<T> {
            return ApiErrorHandling(Status.Success, data, null)
        }

        fun <T> error(message: String?): ApiErrorHandling<T> {
            return ApiErrorHandling(Status.Error, null, message)
        }
    }

}
