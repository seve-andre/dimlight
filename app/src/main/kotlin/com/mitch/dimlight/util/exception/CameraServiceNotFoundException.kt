package com.mitch.dimlight.util.exception

class CameraServiceNotFoundException(
    message: String = "Camera service not available!",
    cause: Throwable? = null
) : Exception(message, cause)
