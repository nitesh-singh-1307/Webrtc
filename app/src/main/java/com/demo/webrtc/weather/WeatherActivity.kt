package com.demo.webrtc.weather
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.demo.newsapplication.base.BaseActivity
import com.demo.newsapplication.utils.App
import com.demo.webrtc.R
import com.demo.webrtc.databinding.ActivityProfileCreationBinding
import com.demo.webrtc.login.LoginActivityViewModel
import com.demo.webrtc.models.NotificationItem
import com.demo.webrtc.models.UserList
import com.demo.webrtc.userlist.NotificationAdapter
import com.demo.webrtc.utils.createAppDialog
import kotlinx.android.synthetic.main.gallery_camera_layout.*
import kotlinx.android.synthetic.main.header_toolbar_black_secound.*
import kotlinx.android.synthetic.main.profile_creation.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import timber.log.Timber
import java.io.File
import java.util.*


@DelicateCoroutinesApi
@ObsoleteCoroutinesApi
class WeatherActivity : BaseActivity<ActivityProfileCreationBinding>(R.layout.actvitiy_weather),
    View.OnClickListener {
    private val viewModel: WeatherViewModel by viewModels()
    var filePath1: String? = null
    private var CameramUri: Uri? = null
    var file1: File? = null
    val app by lazy { App.getInstance() }
    val chatRepository by lazy { app.chatRepository }

    companion object {
        const val REQ_IMAGE1 = 11
    }

    private lateinit var notificationAdapter: NotificationAdapter
    val list = ArrayList<NotificationItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener()
        windowsStatusTrensparent()
        setUpObserver()
    }

    private fun clickListener() {
        viewModel.weatherApps()
        linear2Next.setOnClickListener(this)
        ivLogout.setOnClickListener(this)
//        etPhotoCreation.setOnClickListener(this)
    }

    private fun setUpObserver() {
        viewModel.run {
            apiErrors.observe(this@WeatherActivity) { handleError(it) }
            appLoader.observe(this@WeatherActivity) { updateLoaderUI(it) }
            weather.observe(this@WeatherActivity) {
                Timber.d("setUpObserverLogin::::::::::::::: ${it}")
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
//            R.id.linear1Next -> {
//                val  userList = UserList(
//                    firstname =  etProfileCreationUsername.text.toString(),
//                    lastname =  etProfileCreationFullName.text.toString(),
//                    email =  etEmailCreation.text.toString(),
//                    profile =  etPhotoCreation.text.toString(),
//                )
//                chatRepository.getAllNews(userList)
//            }
            R.id.ivLogout -> {

            }
        }

    }

}

