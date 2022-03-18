package com.demo.webrtc.weather
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.demo.newsapplication.base.BaseViewModel
import com.demo.newsapplication.base.SingleLiveEvent
import com.demo.webrtc.models.Weather
import kotlinx.coroutines.launch

class WeatherViewModel : BaseViewModel() {
    private val _weather = SingleLiveEvent<Weather>()
    val weather: LiveData<Weather> = _weather

//    private val _albumbsphoto = SingleLiveEvent<AlbumbsPhotoRequest>()
//    val albumbsphoto: LiveData<AlbumbsPhotoRequest> = _albumbsphoto

   fun weatherApps(){
       viewModelScope.launch {
           displayLoader()
           processDataEvent(api1Repository.weatherApp()) {
               _weather.postValue(it)
           }
       }
   }
//
//    fun albubs() {
//        viewModelScope.launch {
//            displayLoader()
//            processDataEvent(api1Repository.login()) {
//                _albumbs.postValue(it)
//            }
//        }
//    }
}