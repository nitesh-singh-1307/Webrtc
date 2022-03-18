package com.muzville.data.network.api


import com.demo.newsapplication.models.NewsModuls
import com.demo.webrtc.BuildConfig
import com.demo.webrtc.models.AlbumbsPhotoRequest
import com.demo.webrtc.models.UserInfo
import com.demo.webrtc.models.UserLoginRequest
import com.demo.webrtc.models.Weather
import com.google.gson.GsonBuilder
import com.muzville.data.network.api.IBaseService.Companion.getOkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IApiService1 : IBaseService {
    @GET("everything?q=tesla&from=2022-02-23&sortBy=publishedAt&apiKey=fd21a6cdffa44b4188037d1a94175f65")
    suspend fun getNationalityOptions(): Response<NewsModuls>
//
    //  https://api.openweathermap.org/data/2.5/onecall?lat=12.9082847623315&lon=77.65197822993314&units=imperial&appid=b143bb707b2ee117e62649b358207d3e
    @GET("onecall?lat=12.9082847623315&lon=77.65197822993314&units=imperial&appid=b143bb707b2ee117e62649b358207d3e")
    suspend fun getWeatherApplication(): Response<Weather>

    @GET("albums")
    suspend fun login(): Response<UserLoginRequest>

    @GET("photos?.albumId=1")
    suspend fun albumbphotos(): Response<AlbumbsPhotoRequest>

    companion object {
        fun getService(needEncrypt: Boolean): IApiService1 {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BaseUrl)
                .client(getOkHttpClient(needEncrypt))
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                )
                .addConverterFactory(ScalarsConverterFactory.create())
                .build().create(IApiService1::class.java)
        }
    }
}
