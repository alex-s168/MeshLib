package me.alex_s168.meshlib.exception

/**
 * Exception thrown when a model file is not a valid model file of the format.
 */
class InvalidModelFileException(format: String):
    Exception("Could not load model: File is not a valid model file of the format: $format!")