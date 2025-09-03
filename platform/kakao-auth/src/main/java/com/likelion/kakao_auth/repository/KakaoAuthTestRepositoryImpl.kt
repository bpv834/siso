package com.likelion.kakao_auth.repository

import android.app.Activity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.likelion.domain.auth.model.KakaoTokenResult
import com.likelion.domain.auth.repository.KakaoAuthTestRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class CurrentActivityHolder @Inject constructor() {
    private var ref: WeakReference<Activity>? = null


    fun set(activity: Activity) {
        ref = WeakReference(activity)
    }
    fun clear() { ref?.clear(); ref = null }

    fun getOrNull(): Activity? = ref?.get()
    fun require(): Activity = getOrNull() ?: error("Activity is not set. Call holder.set(activity) before fetch().")
}

class KakaoAuthTestRepositoryImpl @Inject constructor(
    private val activityHolder: CurrentActivityHolder
) : KakaoAuthTestRepository {

    override suspend fun loginWithKakaoToken(): KakaoTokenResult =
        suspendCancellableCoroutine { cont ->
            val activity = activityHolder.getOrNull()
                ?: return@suspendCancellableCoroutine cont.resume(
                    KakaoTokenResult.Error(IllegalStateException("Activity not set"))
                )

            val userApi = UserApiClient.instance

            fun finish(token: OAuthToken?, error: Throwable?) {
                when {
                    error != null -> cont.resume(KakaoTokenResult.Error(error))
                    token != null -> cont.resume(KakaoTokenResult.Success(token.accessToken))
                    else -> cont.resume(KakaoTokenResult.Canceled)
                }
            }

            if (userApi.isKakaoTalkLoginAvailable(activity)) {
                userApi.loginWithKakaoTalk(activity) { token, error ->
                    if (error != null) {
                        val canceled = (error is ClientError && error.reason == ClientErrorCause.Cancelled)
                        if (!canceled) userApi.loginWithKakaoAccount(activity, callback = ::finish)
                        else finish(null, error)
                    } else finish(token, null)
                }
            } else {
                userApi.loginWithKakaoAccount(activity, callback = ::finish)
            }
        }

}