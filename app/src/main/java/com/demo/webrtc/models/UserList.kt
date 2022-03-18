package com.demo.webrtc.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "UserList")
data class UserList(
    var firstname : String ? = null,
    var lastname : String ? = null,
    var email : String ? = null,
    var profile : String ? = null,
): Parcelable
