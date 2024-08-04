package com.example.courseskoinapp.utils

/**
 * @author Muhamed Amin Hassan on 04,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */

sealed class State<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<Q>(data: Q) : State<Q>(data)
    class Error<Q>(message: String) : State<Q>(message = message)
    class Loading<Q> : State<Q>()
    class Ideal<Q> : State<Q>()
}