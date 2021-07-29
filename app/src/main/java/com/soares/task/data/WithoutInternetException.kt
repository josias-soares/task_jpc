package com.soares.task.data

class WithoutInternetException : Exception() {
    override val message: String?
        get() = "No internet connection."
}