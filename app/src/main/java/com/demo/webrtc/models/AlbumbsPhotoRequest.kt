package com.demo.webrtc.models

 class AlbumbsPhotoRequest: ArrayList<albumbs>()

data class albumbs(
    var albumId: Int,
    var id: Int,
    var title: String,
    var url: String? = null,
    var thumbnailUrl : String
)