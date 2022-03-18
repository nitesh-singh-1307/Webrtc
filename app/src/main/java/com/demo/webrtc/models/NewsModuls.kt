package com.demo.newsapplication.models


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "NewsModuls")
data class NewsModuls(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var articles: List<Article>,
    var status: String,
    var totalResults: Int
):Parcelable

@Parcelize
@Entity(tableName = "Article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var author: String?= null,
    var content: String?= null,
    var description: String?= null,
    var publishedAt: String?= null,
    var source: Source?= null,
    var title: String,
    var url: String,
    var urlToImage: String
) : Parcelable

@Parcelize
data class Source(
    var id: String?= null,
    var name: String?= null
) : Parcelable