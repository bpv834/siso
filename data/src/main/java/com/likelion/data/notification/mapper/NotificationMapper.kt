package com.likelion.data.notification.mapper

import com.likelion.domain.notification.model.NotificationModel
import com.likelion.remote.model.request.NotificationAllowRequest

fun NotificationModel.toRequest(): NotificationAllowRequest = NotificationAllowRequest(
    subscribed = this.subscribed
)