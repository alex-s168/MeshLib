package utils

import java.nio.ByteBuffer

fun ByteBuffer.putString(str: String) {
    put(str.toByteArray())
}

fun ByteBuffer.getString(): String {
    var str = ""

    var c = ' '
    while (c != 0.toChar()) {
        c = getChar()
        str += c
    }

    return str
}