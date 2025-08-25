package com.lion.call.call_for_caller

import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likelion.ui.component.full_screen.FullScreenCallEnd
import com.likelion.ui.component.full_screen.FullScreenCallSend
import com.likelion.ui.component.full_screen.FullScreenCallingTry
import com.likelion.ui.component.full_screen.FullScreenWhenCallActive
import timber.log.Timber


@Composable
fun CallerRouter(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    userId: String,
    otherUserId: String,
) {

    Timber.d("userId${userId}/ otherUserId ${otherUserId}")
    CallForCallerScreen(hiltViewModel<CallForCallerScreenViewModel>())
}

@Composable
fun CallForCallerScreen(viewModel: CallForCallerScreenViewModelType) {
    // user, otherUser 받아야함
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column {
        when (uiState.callProgressState) {
            CallForCallerState.Idle -> FullScreenCallSend(
                onClickConfirm = { viewModel.onClickCall(callerId = 0L, receiverId = 1L) },
                onClickBackButton = { viewModel.onClickBackButton() }
            )

            CallForCallerState.Calling -> FullScreenCallingTry(
                otherUser = uiState.otherUser!!,
                onClickButtonCallEnd = {})

            CallForCallerState.CallActive -> FullScreenWhenCallActive(
                user = DummyUser().fakeUser,
                otherUser = DummyUser().fakeOtherUser,
                callDuration = 30,
                isMute = true,
                onClickCallEnd ={},
                isSpeaker = false,
                onClickMute = {},
                onClickSpeaker = {},
                onClickKeepGoing = {}
            )

            CallForCallerState.CallEnd -> FullScreenCallEnd(otherUser = DummyUser().fakeOtherUser,)
            is CallForCallerState.CallFailed -> TODO()
        }

    }
}
