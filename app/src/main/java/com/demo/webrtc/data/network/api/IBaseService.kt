package com.muzville.data.network.api

import com.demo.webrtc.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.*
import java.util.concurrent.TimeUnit

interface IBaseService {
    companion object {
        private const val Authorization = "Authorization"
        private const val Accept = "Accept"
        private const val Content_Type = "Content-Type"
        private const val AcceptLanguage = "Accept-Language"
        private const val TIME_OUT = 120L
        fun getOkHttpClient(needEncrypt: Boolean): OkHttpClient {
//            val pref = App.getInstance().getPref()
            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            httpClient.readTimeout(TIME_OUT, TimeUnit.SECONDS)
            httpClient.writeTimeout(TIME_OUT, TimeUnit.SECONDS)

//            if (needEncrypt) {
//                httpClient.addInterceptor(EncryptionInterceptor())
//                httpClient.addInterceptor(DecryptionInterceptor())
//            }

            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .method(original.method, original.body)
                    .header(Accept, "application/json")
//                    .header(
//                        AcceptLanguage,
//                        LocaleManager.getLanguagePref(App.getInstance().applicationContext)
//                    )
//                if (!pref.comAuthToken.isNullOrBlank()) {
//                    requestBuilder.header(Authorization, "Bearer ${pref.comAuthToken}")
//                }
                return@addInterceptor chain.proceed(requestBuilder.build())
            }
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }
                httpClient.addInterceptor(logging)
            }
            return httpClient.build()
        }
    }
}