package com.example.end

import com.google.gson.Gson
import java.io.File
import java.lang.reflect.Type

object MockUtils {

    private val gson = Gson()

    /**
     * Helper function which will load JSON from
     * the path specified
     *
     * @param path : Path of JSON file
     * @return json : JSON from file at given path
     */
    fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }

    fun serielseObject(obj: Any): String =
        gson.toJson(obj)

    fun serielseGeneric(obj: Any, typeOfSrc: Type): String =
        gson.toJson(obj, typeOfSrc)

}