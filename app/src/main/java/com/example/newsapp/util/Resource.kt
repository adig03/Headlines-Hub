package com.example.newsapp.util


// A sealed class to represent different states of a resource (e.g., data fetching or operation result)
sealed class Resource<T>(
    // 'data' holds the actual data if the operation is successful, nullable by default
    val data: T? = null,

    // 'message' holds an error message in case of failure, also nullable
    val message: String? = null
) {
    // Represents a successful state, contains the result data
    class success<T>(data: T) : Resource<T>(data)

    // Represents an error state, contains an error message and possibly some data (optional)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    // Represents a loading state, typically used when data is being fetched
    class Loading<T> : Resource<T>()
}


