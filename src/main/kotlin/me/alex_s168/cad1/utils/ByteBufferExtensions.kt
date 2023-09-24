package me.alex_s168.cad1.utils

import java.nio.ByteBuffer

/**
 * Puts a string into the buffer with a null terminator.
 */
fun ByteBuffer.putString(str: String) {
    put(str.toByteArray())
    put(0.toByte())
}

/**
 * Reads a null-terminated string from the buffer.
 */
fun ByteBuffer.getString(): String {
    var str = ""

    var c = ' '
    while (c != 0.toChar()) {
        c = getChar()
        str += c
    }

    return str
}