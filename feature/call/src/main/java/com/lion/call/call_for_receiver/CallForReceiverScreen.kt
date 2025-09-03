package com.lion.call.call_for_receiver

import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.likelion.ui.component.bottomsheet.BottomSheetReport
import com.likelion.ui.component.dialog.ReportPopUpDialog
import com.likelion.ui.component.full_screen.FullScreenCallEndReview
import com.likelion.ui.component.full_screen.FullScreenCallInit
import com.likelion.ui.component.full_screen.FullScreenCallingTry
import com.likelion.ui.component.full_screen.FullScreenWhenCallActive
import com.lion.call.call_for_caller.CallForCallerScreenViewModel
import com.lion.call.call_for_caller.CallForCallerScreenViewModelType
import com.lion.call.call_for_caller.CallForCallerState
import com.lion.call.call_for_caller.CallUiEvent
import com.lion.call.call_for_caller.CallUiState
import com.lion.call.call_for_caller.DummyUser

@Composable
fun CallForReceiverRouter(
    modifier: Modifier = Modifier,
    otherUserId: Long,
    onNavigateUp: () -> Unit,
    viewModel: CallForCallerScreenViewModelType = hiltViewModel<CallForReceiverScreenViewModel>(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 1. 이벤트 처리 로직만 Router에 남겨둡니다.
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is CallUiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is CallUiEvent.NavigateUp -> onNavigateUp()
                is CallUiEvent.ShowError -> {/* 에러 처리 */ }
                is CallUiEvent.ShowSnackbar -> {/* 스낵바 처리 */ }
                is CallUiEvent.ShowReportSheet -> { /* 시트 관련 로직은 Screen에서 처리하는 게 더 좋습니다. */ }
            }
        }
    }

    // 2. uiState와 viewModel을 Screen으로 넘겨줍니다.
    CallForReceiverScreen(
        uiState = uiState,
        viewModel = viewModel,
        onNavigateUp = onNavigateUp,
        otherUserId = otherUserId
    )
}
// CallForReceiverScreen.kt (완성된 스크린)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallForReceiverScreen(
    uiState: CallUiState,
    viewModel: CallForCallerScreenViewModelType,
    onNavigateUp: () -> Unit,
    otherUserId: Long,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        when (uiState.callProgressState) {
            CallForCallerState.Idle -> FullScreenCallInit(
                onClickConfirm = { viewModel.onClickCall(receiverId = otherUserId) },
                onClickBackButton = { viewModel.onClickBackButton() }
            )
            CallForCallerState.Calling -> FullScreenCallingTry(
                otherUser = DummyUser().fakeOtherUser,
                onClickButtonCallEnd = { viewModel.onClickEndCall() }
            )
            CallForCallerState.CallActive -> FullScreenWhenCallActive(
                user = DummyUser().fakeUser,
                otherUser = DummyUser().fakeOtherUser,
                callDuration = uiState.callDuration,
                isMute = uiState.isMuted,
                onClickCallEnd = { viewModel.onClickEndCall() },
                isSpeaker = uiState.isSpeakerOn,
                onClickMute = { viewModel.toggleMute() },
                onClickSpeaker = { viewModel.toggleSpeaker() },
                onClickKeepGoing = {},
                startCallTimer = { viewModel.startCallTimer() }
            )
            CallForCallerState.CallEnd -> FullScreenCallEndReview(
                caller = DummyUser().fakeOtherUser,
                onClickReport = { viewModel.onClickReportButton() },
                onClickAnother = { viewModel.onClickEndCall() },
                onClickKeepGoing = {}
            )
        }
    }

    if (uiState.isOpenReportSheet) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.onClickReportButton() },
            sheetState = sheetState
        ) {
            BottomSheetReport(
                badUser = DummyUser().fakeOtherUser,
                onDismissRequest = { viewModel.onClickReportButton() },
                onClickReport = { viewModel.onClickConfirmPopup() }
            )
        }
    }

    if (uiState.isOpenConfirmPopup) {
        ReportPopUpDialog(onClickClose = { viewModel.onClickEndCall() })
    }
}