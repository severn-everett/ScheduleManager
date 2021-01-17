package com.severett.schedulemanager.model.exception

class ResourceNotFoundException(message: String) : Exception(message) {
    // Do not fill in the stacktrace, as it's not going
    // to be used.
    override fun fillInStackTrace() = this
}