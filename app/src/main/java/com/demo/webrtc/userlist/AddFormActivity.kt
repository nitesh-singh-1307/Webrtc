package com.demo.webrtc.userlist
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
import kotlinx.android.synthetic.main.profile_creation.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import java.io.File
import java.util.*


@DelicateCoroutinesApi
@ObsoleteCoroutinesApi
class AddFormActivity : BaseActivity<ActivityProfileCreationBinding>(R.layout.profile_creation),
    View.OnClickListener {
    private val viewModel: LoginActivityViewModel by viewModels()
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
        checkPermission(Manifest.permission.CAMERA, REQ_IMAGE1);

//        setUpObserver()
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@AddFormActivity, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this@AddFormActivity, arrayOf(permission), requestCode)
        } else {
//            Toast.makeText(this@EditPhotoActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }


    private fun clickListener() {
        linear2Next.setOnClickListener(this)
        linear1Next.setOnClickListener(this)
        etPhotoCreation.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.linear1Next -> {
                val  userList = UserList(
                    firstname =  etProfileCreationUsername.text.toString(),
                    lastname =  etProfileCreationFullName.text.toString(),
                    email =  etEmailCreation.text.toString(),
                    profile =  etPhotoCreation.text.toString(),
                )
                chatRepository.getAllNews(userList)
            }
            R.id.linear2Next -> {

            }
            R.id.etPhotoCreation ->{
                CustomDailogBoxCameraAndGallery(REQ_IMAGE1)

            }
        }

    }

    private fun CustomDailogBoxCameraAndGallery(REQ_IMAGE1: Int) {
        try {
            val dialog = createAppDialog(R.layout.gallery_camera_layout)
            dialog.run {
                window?.setDimAmount(0.80f)
                imgGallery.setOnClickListener {
                    openGallery(REQ_IMAGE1)
                    dialog.dismiss()
                }
                imgCamera.setOnClickListener {
                    capturePhoto(REQ_IMAGE1)
                    dialog.dismiss()
                }
            }
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openGallery(requireRequestCode: Int) {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, requireRequestCode)
            }
        }
    }

    private fun capturePhoto(requireRequestCode: Int) {
        val capturedImage = File(externalCacheDir, "${System.currentTimeMillis()}.png")
        if (capturedImage.exists()) {
            capturedImage.delete()
        }
        capturedImage.createNewFile()
        CameramUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(
                this, "com.demo.webrtc.fileprovider",
                capturedImage
            )
        } else {
            Uri.fromFile(capturedImage)
        }
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, CameramUri)
        startActivityForResult(intent, requireRequestCode)
    }
}

