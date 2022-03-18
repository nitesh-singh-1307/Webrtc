package com.demo.newsapplication.data.database.entities

import androidx.room.TypeConverter
import com.demo.newsapplication.models.Source
import org.json.JSONObject

object SourceTypeConverters {
    @TypeConverter
    fun ConvertSource(source: Source?): String? {
        return JSONObject().apply {
            put("id", source?.id)
            put("name", source?.name)
        }.toString()
    }

    @TypeConverter
    fun toSource(source: String): Source? {
//        val json = JSONObject(source)
//        return Source(json.get("id") as String?, json.getString("name"))
        return source?.let { Source(it) }

    }
}