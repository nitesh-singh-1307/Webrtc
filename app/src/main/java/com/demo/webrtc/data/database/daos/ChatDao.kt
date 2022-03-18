package com.demo.newsapplication.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.demo.newsapplication.models.Article
import com.demo.newsapplication.models.NewsModuls
import com.demo.webrtc.models.UserList
import kotlinx.coroutines.flow.Flow


@Dao
interface ChatDao {
    @Query("SELECT * FROM  UserList")
    fun getAllData(): List<UserList>

    @Query("select * from article ORDER BY date(article.publishedAt) asc")
    fun getAscAllData(): List<Article>

//    @Query("SELECT * FROM  NewsModuls")
    @Query("SELECT * FROM NewsModuls WHERE id=:id")
    fun getNewsModuleData(id:String): NewsModuls

    @Insert
    suspend fun addAll(article: Article)

     @Insert
     fun addAllNews(newsModel: UserList)

    @Update
    suspend fun updateArticle(article: Article)


    @Query("DELETE FROM UserList WHERE firstname = :firstname")
    fun deleteByUserName(firstname: String)

//    @Delete
//    suspend fun deleteArticle(article: UserList)

}