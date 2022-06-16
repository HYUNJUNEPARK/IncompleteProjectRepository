package com.june.phonenumberbackup.fileIO

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import kotlin.Throws
import kotlin.jvm.JvmStatic

object testReader {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
            FileReader("d:\\file.txt")
        )
        var str: String?
        while (reader.readLine().also { str = it } != null) {
            println(str)
        }
        reader.close()
    }
}