package com.demo.webrtc.login

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.demo.newsapplication.base.BaseActivity
import com.demo.webrtc.R
import com.demo.webrtc.databinding.ActvitiyLoginBinding
import com.demo.webrtc.userlist.UserListActivity
import com.demo.webrtc.utils.gone
import com.demo.webrtc.utils.isEmailValid
import com.demo.webrtc.utils.visible
import kotlinx.android.synthetic.main.actvitiy_login.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import timber.log.Timber
import java.util.*


@DelicateCoroutinesApi
@ObsoleteCoroutinesApi
class LoginActivity : BaseActivity<ActvitiyLoginBinding>(R.layout.actvitiy_login),
    View.OnClickListener {
    private val viewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener()
        setUpObserver()
    }


    private fun clickListener() {
        binding.tvLogin.setOnClickListener(this)
//        binding.tvForgotPassword.setOnClickListener(this)
        viewModel.albubs()
        viewModel.albumbphotos()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tvLogin -> {
                if (validation()) {
//                    val userLogin = UserLoginRequest(
//                        binding.etUsernames.text.toString(),
//                        binding.etUsername.text.toString(),
//                        binding.etPassword.text.toString(),
//                    )
//                    pref.currentLocation?.lat = location?.latitude!!

//                    pref.loginString?.name = binding.etUsernames.text.toString()
//                    pref.loginString?.email = binding.etUsername.text.toString()
//                    pref.loginString?.phone = binding.etPassword.text.toString()
                    viewModel.albubs()
                    viewModel.albumbphotos()
                    startActivity<UserListActivity>()

//                    if (pref.loginString?.name == binding.etUsernames.text.toString() && pref.loginString?.email == binding.etUsername.text.toString()) {
//                        startActivity<InformationActivity>()
//                    } else {
//                        toast(R.string.str_not_match)
//                    }
                }
            }
        }

    }


    private fun setUpObserver() {
        viewModel.run {
            apiErrors.observe(this@LoginActivity) { handleError(it) }
            appLoader.observe(this@LoginActivity) { updateLoaderUI(it) }
            albumbs.observe(this@LoginActivity) {
                Timber.d("setUpObserverLogin::::::::::::::: ${it}")
            }

            albumbsphoto.observe(this@LoginActivity) {
                Timber.d("albumbsphoto::::::::::::::: ${it}")
            }

        }

    }


    private fun validation(): Boolean {
        if (etUsernames.text.toString().trim() == "") {
            etUsernames.setBackgroundResource(R.drawable.drawable_error_background)
            llErrors.visible()
            return false
        } else {
            etUsernames.setBackgroundResource(R.drawable.drawable_normal_background)
            llErrors.gone()
        }




        if (etUsername.text.toString().trim() == "") {
            etUsername.setBackgroundResource(R.drawable.drawable_error_background)
            llError.visible()
            return false
        } else {
            etUsername.setBackgroundResource(R.drawable.drawable_normal_background)
            llError.gone()
        }


        if (!etUsername.text.toString().trim().isEmailValid) {
            etUsername.setBackgroundResource(R.drawable.drawable_error_background)
            llError.visible()
            return false
        } else {
            etUsername.setBackgroundResource(R.drawable.drawable_normal_background)
            llError.gone()
        }
//
//        if (etPassword.text.toString().trim() == "") {
//            etPassword.setBackgroundResource(R.drawable.drawable_error_background)
//            ll2Error.visible()
//            return false
//        } else {
//            etPassword.setBackgroundResource(R.drawable.drawable_normal_background)
//            ll2Error.gone()
//        }
        return true

    }

}

