package com.demo.newsapplication.data.network.repositary

import com.demo.webrtc.models.UserLoginRequest
import com.muzville.data.network.api.IApiService1


class Api1Repository(private val apiService: IApiService1) : BaseRepository() {
    suspend fun getNationalityOptions() = callApi { apiService.getNationalityOptions() }
    suspend fun login() = callApi { apiService.login() }
    suspend fun albumbphotos() = callApi { apiService.albumbphotos() }
    suspend fun weatherApp() = callApi { apiService.getWeatherApplication() }

    companion object {
        @Volatile
        private var instance: Api1Repository? = null
        fun getInstance(): Api1Repository {
            return instance ?: synchronized(this) {
                instance ?: Api1Repository(IApiService1.getService(true))
                    .also {
                        instance = it
                    }
            }
        }


    }
}