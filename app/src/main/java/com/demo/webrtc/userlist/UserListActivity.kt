package com.demo.webrtc.userlist

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.newsapplication.base.BaseActivity
import com.demo.newsapplication.utils.App
import com.demo.webrtc.R
import com.demo.webrtc.databinding.ActvitiyInformationBinding
import com.demo.webrtc.login.LoginActivityViewModel
import com.demo.webrtc.models.NotificationItem
import com.demo.webrtc.models.UserList
import kotlinx.android.synthetic.main.actvitiy_login.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import java.util.*


@DelicateCoroutinesApi
@ObsoleteCoroutinesApi
class UserListActivity : BaseActivity<ActvitiyInformationBinding>(R.layout.actvitiy_information),
    View.OnClickListener {
    private val viewModel: LoginActivityViewModel by viewModels()
    private lateinit var notificationAdapter: NotificationAdapter
    var articleList: List<UserList>? = null
    val app by lazy { App.getInstance() }
    val chatRepository by lazy { app.chatRepository }

    val list = ArrayList<NotificationItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener()
        windowsStatusTrensparent()
//        setUpObserver()
    }


    private fun clickListener() {
//        val userlismodel = NotificationItem()
//        userlismodel.username = "A"
//        list.add(userlismodel)
//        userlismodel.username = "B"
//        list.add(userlismodel)
//        userlismodel.username = "C"
//        list.add(userlismodel)
//        userlismodel.username = "D"
//        list.add(userlismodel)
//        userlismodel.username = "E"
//        list.add(userlismodel)
//        userlismodel.username = "F"
//        list.add(userlismodel)
//        userlismodel.username = "G"
//        userlismodel.username = "H"
//        userlismodel.username = "I"
//        userlismodel.username = "N"
//        list.add(userlismodel)
        binding.toolbar.ivAdd.setOnClickListener(this)
        notificationAdapter = NotificationAdapter(this@UserListActivity)
        binding.rvNotificationDetail.layoutManager = LinearLayoutManager(this@UserListActivity)
        binding.rvNotificationDetail.isNestedScrollingEnabled = false
        articleList = chatRepository.getAllNewsData()

        if (articleList?.size != 0) {
            notificationAdapter.addAll(articleList!!)
        }
        binding.rvNotificationDetail.adapter = notificationAdapter
        notificationAdapter.setItemClickListener { view, position, _notificationItem ->
            when (view.id) {
                R.id.notificationConstraint -> {
                    chatRepository.getDelete(_notificationItem.firstname!!)
//                    startActivity<ViewProfileActivity>(USER_ID to notificationItem.sender_id , ViewProfileActivity.USER_TIMER_COUNTER_FLAG to "0")
                }
            }
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivAdd -> {

            }

        }

    }


}

