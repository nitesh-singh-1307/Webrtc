package com.demo.newsapplication.data.database.entities

import androidx.room.TypeConverter
import com.demo.newsapplication.models.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ArticleTypeConvertor {
    @TypeConverter
    fun ConvertSource(articleList: List<Article>?): String {

        return Gson().toJson(
            articleList, object : TypeToken<List<Article>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun toSource(articleJson: String): List<Article> {
        return Gson().fromJson(
            articleJson, object : TypeToken<List<Article>>() {}.type
        ) ?: emptyList()

    }

}