package model.exception

class InvalidModelFileException(format: String):
    Exception("Could not load model: File is not a valid model file of the format: $format!")