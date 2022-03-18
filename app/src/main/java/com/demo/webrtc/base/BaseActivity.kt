package com.demo.newsapplication.base

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.demo.newsapplication.data.network.models.ResponseCode
import com.demo.newsapplication.utils.App
import com.demo.newsapplication.utils.AppLoader
import com.demo.webrtc.R
import com.demo.webrtc.data.pref.SharedPref
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.channels.ReceiveChannel
import org.jetbrains.anko.contentView
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by pragya on 25/05/21
 */

abstract class BaseActivity<VB : ViewDataBinding>(private val layoutRes: Int) : AppCompatActivity(){
    private var originalMode: Int? = null
    private var mAlertDialog: AlertDialog? = null
    protected lateinit var binding: VB
    protected val listSubscription = ArrayList<ReceiveChannel<*>>()
    private val appLoader: AppLoader by lazy { AppLoader(this) }
    val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    val pref: SharedPref by lazy { App.getInstance().getPref() }
//    val dao: AppDao by lazy { App.getInstance().getDao() }
//     val localeDelegate: LocaleHelperActivityDelegate = LocaleHelperActivityDelegateImpl()
//    override fun getDelegate() = localeDelegate.getAppCompatDelegate(super.getDelegate())

//    override fun attachBaseContext(newBase: Context) {
//        super.attachBaseContext(localeDelegate.attachBaseContext(newBase))
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        localeDelegate.onCreate(this)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        originalMode = window?.attributes?.softInputMode
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }


    override fun onResume() {
        super.onResume()
//        localeDelegate.onResumed(this)
    }


    override fun onPause() {
        super.onPause()
//        localeDelegate.onPaused()
    }





    protected fun windowsStatusTrensparent() {
        window?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                var flags = decorView.systemUiVisibility
                flags =
                    flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                decorView.systemUiVisibility = flags
            }
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.WHITE
        }
    }


    /**
     * Use this method when user manually change the language from app.
     * Usage:
     *   setNewLocale(LocaleManager.ENGLISH)
     *   setNewLocale(LocaleManager.GERMAN)
     * */
//    protected fun setNewLocale(language: String) {
//        LocaleManager.setNewLocale(this, language)
//        recreate()
//    }


    override fun onDestroy() {
        super.onDestroy()
        listSubscription.forEach { it.cancel() }
        originalMode?.let { window?.setSoftInputMode(it) }
        listSubscription.forEach { it.cancel() }
    }

    open fun showMessage(message: String) {
        this.contentView?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

    open fun showError(errorMessage: String) {
        showMessage(errorMessage)
    }

    protected fun updateLoaderUI(isShow: Boolean) {
        if (isShow) appLoader.show() else appLoader.dismiss()
    }
    fun handleError(throwable: Throwable) {
        when (throwable) {
            is HttpException -> {
                handleResponseError(throwable)
            }
            is ConnectException -> {
                showMessage(getString(R.string.msg_no_internet))
            }
            is SocketTimeoutException -> {
                showMessage(getString(R.string.time_out))
            }
        }
    }
    private fun handleResponseError(throwable: HttpException) {
        when (throwable.code()) {
            ResponseCode.InvalidData.code -> {
                val errorRawData = throwable.response()?.errorBody()?.string()?.trim()
                if (!errorRawData.isNullOrEmpty()) {
                    val jsonObject = JSONObject(errorRawData)
                    val jObject = jsonObject.optJSONObject("errors")
                    if (jObject != null) {
                        val keys: Iterator<String> = jObject.keys()
                        if (keys.hasNext()) {
                            val msg = StringBuilder()
                            while (keys.hasNext()) {
                                val key: String = keys.next()
                                if (jObject.get(key) is String) {
                                    msg.append("- ${jObject.get(key)}\n")
                                }
                            }
                            errorDialog(msg.toString(), "Alert")
                        } else {
                            errorDialog(jsonObject.optString("message", ""))
                        }
                    } else {
                        errorDialog(JSONObject(errorRawData).optString("message"), "Alert")
                    }
                }
            }
            ResponseCode.Unauthenticated.code -> {
                AlertDialog.Builder(this)
                    .setTitle(R.string.alert)
                    .setPositiveButton(R.string.ok) { dialog, id ->
                        dialog.dismiss()
//                        onAuthFail()
                    }
                    .setMessage(getString(R.string.userUnauthenticated))
                    .create()
                    .apply {
                        setCanceledOnTouchOutside(false)
                        setCancelable(false)
                        show()
                    }
            }
            ResponseCode.ForceUpdate.code,
            ResponseCode.InternalServerError.code -> errorDialog("Internal Server error")
            ResponseCode.BadRequest.code,
            ResponseCode.Unauthorized.code,
            ResponseCode.NotFound.code,
            ResponseCode.RequestTimeOut.code,
            ResponseCode.Conflict.code,
            ResponseCode.Blocked.code -> {
                val errorRawData = throwable.response()?.errorBody()?.string()?.trim()
                if (!errorRawData.isNullOrEmpty()) {
                    errorDialog(JSONObject(errorRawData).optString("message", ""))
                }
            }
        }
    }




    private fun errorDialog(optString: String?, title: String = getString(R.string.app_name)) {
//        optString?.let {
//            alert(it, title) { okButton { } }.show()
//        }
        val builder: AlertDialog.Builder? = this.let {
            AlertDialog.Builder(it)
        }

        builder?.setMessage(optString)?.setTitle(title)
        builder?.apply {
            setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
        }

        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.setCancelable(true)
    }


    private fun showfullscreen() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

    }

    override fun onStop() {
        super.onStop()
        if (mAlertDialog != null && mAlertDialog!!.isShowing) {
            mAlertDialog!!.dismiss()
        }
    }


    /**
     * This method shows dialog with given title & message.
     * Also there is an option to pass onClickListener for positive & negative button.
     *
     * @param title                         - dialog title
     * @param message                       - dialog message
     * @param onPositiveButtonClickListener - listener for positive button
     * @param positiveText                  - positive button text
     * @param onNegativeButtonClickListener - listener for negative button
     * @param negativeText                  - negative button text
     */
    protected open fun showAlertDialog(
        title: String?, message: String?,
        onPositiveButtonClickListener: DialogInterface.OnClickListener?,
        positiveText: String,
        onNegativeButtonClickListener: DialogInterface.OnClickListener?,
        negativeText: String
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener)
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener)
        mAlertDialog = builder.show()
    }


    fun hideSoftKeyboard(view: View) {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun convertCmToFeet(cm: Int): String {
        val feet: Double = 0.0328 * cm
        Timber.d("checkFeet ::::${feet}")
        val feetArray = String.format("%.1f", feet).split(".")
        //val feetArray = getFeet.split(".")
        Timber.d("checkArrayFeet ::::${feetArray.size}")
        return "${feetArray[0]}'${feetArray[0]}"
    }


}

