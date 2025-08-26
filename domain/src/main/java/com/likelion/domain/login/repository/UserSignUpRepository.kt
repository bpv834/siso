package com.likelion.domain.login.repository

import com.likelion.domain.login.model.UserSignUpProfile
import kotlinx.coroutines.flow.Flow

/**
 * 회원가입 과정에서 사용할 사용자 프로필 관련 저장소 인터페이스
 */
interface UserSignUpRepository {

    /**
     * 임시로 저장된 사용자 프로필을 가져옴
     * - Flow 형태로 반환되어, 데이터가 변경되면 자동으로 구독자에게 전달됨
     */
    fun getTemporaryUserProfile(): Flow<UserSignUpProfile>

    /**
     * 사용자 프로필을 임시로 저장함
     * - 회원가입 도중 앱이 종료되거나 화면을 이동해도 데이터를 복원할 수 있음
     */
    suspend fun saveTemporaryUserProfile(profile: UserSignUpProfile)

    /**
     * 임시로 저장된 사용자 프로필을 모두 삭제함
     * - 회원가입 취소 시 사용
     */
    suspend fun clearTemporaryUserProfile()

    /**
     * 사용자 프로필을 서버에 최종 등록함
     * - 회원가입 완료 시 서버에 데이터를 전송하는 역할
     */
    suspend fun registerProfileToServer(profile: UserSignUpProfile)
}