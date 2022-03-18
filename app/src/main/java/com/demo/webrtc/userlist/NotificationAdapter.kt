package com.demo.webrtc.userlist

import android.annotation.SuppressLint
import android.view.View
import coil.load
import com.demo.newsapplication.base.BaseAdapter
import com.demo.webrtc.R
import com.demo.webrtc.databinding.NotificationItemLayoutBinding
import com.demo.webrtc.models.UserList

class NotificationAdapter(notificationActivity: UserListActivity) :
    BaseAdapter<NotificationItemLayoutBinding, UserList>(R.layout.notification_item_layout) {
    override fun setClickableView(binding: NotificationItemLayoutBinding): List<View?> {
        return when (binding) {is NotificationItemLayoutBinding -> { listOf(binding.notificationConstraint) }else -> listOf(binding.root) }
    }
    override fun setLongClickableView(binding: NotificationItemLayoutBinding): List<View?> =
        listOf()

    @SuppressLint("SimpleDateFormat")
    override fun onBind(
        binding: NotificationItemLayoutBinding,
        position: Int,
        item: UserList,
        payloads: MutableList<Any>?
    ) {
        binding.run {
            if (item.profile.isNullOrEmpty()) {
                rivNotificationProfile.load(R.drawable.ic_placeholder) {
                   placeholder(R.drawable.ic_placeholder)
                }
            }
            tvNotificationPlan.text = item.firstname

        }
    }

}