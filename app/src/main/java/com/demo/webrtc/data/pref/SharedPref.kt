package com.demo.webrtc.data.pref

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.demo.webrtc.models.UserLoginRequest
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@RequiresApi(Build.VERSION_CODES.M)
@ObsoleteCoroutinesApi
@DelicateCoroutinesApi
class SharedPref(context: Context, private val gson: Gson) : EncryptedPreferences(context) {
    private inline fun <reified T> toJson(value: T?) =
        if (value == null) null else gson.toJson(value)

    private inline fun <reified T> fromJson(value: String?) =
        if (value.isNullOrEmpty()) null else gson.fromJson(value, T::class.java)

    //<editor-fold desc="Clear App Data">

    //</editor-fold>



//    var login: String?
//        set(value) = setString(this::login.name, value)
//        get() = getString(this::login.name)


//    var isSelectedProfession: Boolean?
//        get() = getBoolean(this::isSelectedProfession.name, false)
//        set(value) = setBoolean(this::isSelectedProfession.name, value!!)


//    var loginString: String?
//        get() = getString(this::loginString.name)
//        set(value) = setString(this::loginString.name, value)


    var loginString: UserLoginRequest?
        set(value) = setString(this::loginString.name, toJson(value))
        get() = try {
            fromJson(getString(this::loginString.name))
        } catch (e: Exception) {
            null
        }


    var isChatLoaded: Boolean
        get() = getBoolean(this::isChatLoaded.name, false)
        set(value) = setBoolean(this::isChatLoaded.name, value)
}