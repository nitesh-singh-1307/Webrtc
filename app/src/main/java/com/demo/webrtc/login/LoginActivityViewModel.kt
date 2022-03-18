package com.demo.webrtc.login
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.demo.newsapplication.base.BaseViewModel
import com.demo.newsapplication.base.SingleLiveEvent
import com.demo.webrtc.models.AlbumbsPhotoRequest
import com.demo.webrtc.models.UserLoginRequest
import kotlinx.coroutines.launch

class LoginActivityViewModel : BaseViewModel() {
    private val _albumbs = SingleLiveEvent<UserLoginRequest>()
    val albumbs: LiveData<UserLoginRequest> = _albumbs

    private val _albumbsphoto = SingleLiveEvent<AlbumbsPhotoRequest>()
    val albumbsphoto: LiveData<AlbumbsPhotoRequest> = _albumbsphoto

   fun albumbphotos(){
       viewModelScope.launch {
           displayLoader()
           processDataEvent(api1Repository.albumbphotos()) {
               _albumbsphoto.postValue(it)
           }
       }
   }

    fun albubs() {
        viewModelScope.launch {
            displayLoader()
            processDataEvent(api1Repository.login()) {
                _albumbs.postValue(it)
            }
        }
    }
}