package com.lion.call.call_for_caller

import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
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
import com.likelion.ui.component.full_screen.FullScreenCallSend
import com.likelion.ui.component.full_screen.FullScreenCallingTry
import com.likelion.ui.component.full_screen.FullScreenWhenCallActive
import timber.log.Timber


@Composable
fun CallerRouter(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    otherUserId: Long,
    onNavigateUp: () -> Unit
) {

    Timber.d("otherUserId ${otherUserId}")
    CallForCallerScreen(hiltViewModel<CallForCallerScreenViewModel>(), onNavigateUp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallForCallerScreen(viewModel: CallForCallerScreenViewModelType, onNavigateUp: () -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is CallUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is CallUiEvent.NavigateUp -> {
                    onNavigateUp()
                }

                is CallUiEvent.ShowError -> TODO()
                is CallUiEvent.ShowSnackbar -> TODO()
                is CallUiEvent.ShowReportSheet -> {
                    viewModel.onClickReportButton() // 신고 이벤트를 발생시킨다. 상태가 변화되고 감지해서 시트를 띄움
                }
            }
        }
    }
    // user, otherUser 받아야함
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        when (uiState.callProgressState) {
            // 전화 대기상태 초기 안내문 스크린
            CallForCallerState.Idle -> FullScreenCallSend(
                onClickConfirm = { viewModel.onClickCall(callerId = 0L, receiverId = 0L) },
                onClickBackButton = { viewModel.onClickBackButton() }
            )
            //  상대방과 연결중 채널에 발신자만 들어가있는상태
            CallForCallerState.Calling -> FullScreenCallingTry(
                otherUser = DummyUser().fakeOtherUser,
                onClickButtonCallEnd = { viewModel.onClickEndCall() })

            // 발신자 , 수신자 전부 입장한 상태
            CallForCallerState.CallActive -> FullScreenWhenCallActive(
                user = DummyUser().fakeUser,
                otherUser = DummyUser().fakeOtherUser,
                callDuration = uiState.callDuration,
                isMute = uiState.isMuted,
                onClickCallEnd = { viewModel.onClickEndCall() },
                isSpeaker = uiState.isSpeakerOn,
                onClickMute = { viewModel.toggleMute() },
                onClickSpeaker = { viewModel.toggleSpeaker() },
                // 인연이어가기 버튼 누르면 대화방으로 이동
                onClickKeepGoing = {},
                startCallTimer = { viewModel.startCallTimer() },
            )
            // 통화 종료 후 인연이어갈지 말지 선택하는 상태
            CallForCallerState.CallEnd -> FullScreenCallEndReview(
                caller = DummyUser().fakeOtherUser,
                onClickReport = { viewModel.onClickReportButton() }, // 바텀시트 열기
                onClickAnother = { viewModel.onClickEndCall() },
                onClickKeepGoing = {}
            )
        }

    }
    if (uiState.isOpenReportSheet) ModalBottomSheet(
        onDismissRequest = {}, sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true // 중간 상태 건너뛰기
        )
    ) {
        BottomSheetReport(
            badUser = DummyUser().fakeOtherUser,
            onDismissRequest = { viewModel.onClickReportButton() },
            onClickReport = { viewModel.onClickConfirmPopup() })
    }
    if (uiState.isOpenConfirmPopup) ReportPopUpDialog(onClickClose = { viewModel.onClickEndCall() })
}
