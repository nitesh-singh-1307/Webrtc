package com.demo.newsapplication.data.database.repository

import com.demo.newsapplication.data.database.daos.ChatDao
import com.demo.newsapplication.models.Article
import com.demo.newsapplication.models.NewsModuls
import com.demo.newsapplication.utils.App
import com.demo.webrtc.models.UserList
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

@DelicateCoroutinesApi
@ObsoleteCoroutinesApi
class ChatRepository(private val chatDao: ChatDao) {
    private val app by lazy { App.getInstance() }

    //    private val pref by lazy { app.getPref() }
    private val repositoryScope = CoroutineScope(Dispatchers.IO)
    private val serverDateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

    companion object {
        const val TAG = "ChatRepository"
    }


//    fun clearAll() {
//        repositoryScope.launch { chatDao.clearAll() }
//    }


    fun getAllNewsData(): List<UserList> {
        return chatDao.getAllData()
    }


    fun getDelete(username: String) {
        chatDao.deleteByUserName(username)
    }

    fun getAscAllNewsData(): List<Article> {
        return chatDao.getAscAllData()
    }

    fun getNewsModelData(): NewsModuls {
        return chatDao.getNewsModuleData("1")
    }

    fun getAllNews(newsModuls: UserList) {
        return chatDao.addAllNews(newsModuls)
    }


    fun insertMessage(message: Article) {
        val chatMessages = Article(
            author = message.author,
            content = message.content,
            description = message.description,
            publishedAt = message.publishedAt,
            title = message.title,
            url = message.url,
            urlToImage = message.urlToImage
        )
        repositoryScope.launch {
//            chatDao.insertChatUsers(chatMessages)
            chatDao.addAll(chatMessages)
        }
    }

}


