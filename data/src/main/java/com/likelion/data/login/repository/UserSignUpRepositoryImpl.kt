package com.likelion.data.login.repository

import com.likelion.data.login.mapper.toUserProfileRequest
import com.likelion.domain.login.model.UserSignUpProfile
import com.likelion.domain.login.repository.UserSignUpRepository
import com.likelion.remote.api.UserSignUpApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryUserSignUpRepositoryImpl @Inject constructor(
    private val userSignUpApi: UserSignUpApi // Retrofit API 서비스 주입
) : UserSignUpRepository {

    private var temporaryProfile = UserSignUpProfile()

    override suspend fun saveTemporaryUserProfile(profile: UserSignUpProfile) {
        temporaryProfile = profile
    }

    override suspend fun getTemporaryUserProfile(): UserSignUpProfile = temporaryProfile

    override suspend fun clearTemporaryUserProfile() {
        temporaryProfile = UserSignUpProfile()
    }

    override suspend fun registerProfileToServer(refreshToken: String, profile: UserSignUpProfile) {
        // 1. 프로필 정보 등록
        // domain 모델 (UserSignUpProfile)을 remote DTO (UserProfileRequest)로 변환합니다.
        val userProfileRequest = profile.toUserProfileRequest()
        val profileResponse = userSignUpApi.registerUserProfile(
            refreshToken = refreshToken,
            request = userProfileRequest,
        )

        if (!profileResponse.isSuccessful) {
            // 프로필 등록 실패 시 예외 처리
            throw Exception("Failed to register user profile: ${profileResponse.errorBody()?.string()}")
        }
        val registeredUserProfile = profileResponse.body()
        // TODO: 서버 응답에 userId가 있다면 사용하고, 없다면 다른 방법으로 사용자 ID를 확보해야 합니다.
        // 현재 서버 응답 DTO (UserProfileResponseDto)에 userId 필드가 없으므로,
        // 이 부분은 서버 API 응답에 따라 수정이 필요합니다.
        // 예시를 위해 임시 userId를 사용하거나, 다른 API에서 userId를 가져오는 로직이 필요할 수 있습니다.
        // 여기서는 임시로 1L을 사용하거나, 실제 서버 응답에서 추출하는 로직을 가정합니다.
        val userId: Long = 1L // TODO: 실제 userId를 서버 응답에서 추출하거나 다른 곳에서 가져오도록 수정 필요

        // userId가 없다면 파일 업로드를 진행할 수 없으므로 예외 처리
        // if (userId == null) {
        //     throw Exception("User ID not returned from profile registration.")
        // }

        // 2. 사진 파일 업로드
        for (path in profile.photoPaths) {
            val file = File(path)
            if (file.exists()) {
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                // "image"는 서버에서 파일을 받을 MultipartForm-Data의 필드명입니다. (e.g., @RequestPart("image") MultipartFile image)
                val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)
                // "userId"는 서버에서 추가 데이터를 받을 MultipartForm-Data의 필드명입니다. (e.g., @RequestPart("userId") Long userId)
                val userIdPart = userId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                val imageUploadResponse = userSignUpApi.uploadProfileImage(imagePart, userIdPart)
                if (!imageUploadResponse.isSuccessful) {
                    // 이미지 업로드 실패 처리 (부분 실패도 고려하여 로그만 남기고 계속 진행할 수도 있습니다)
                    println("Failed to upload image: ${file.name}, Error: ${imageUploadResponse.errorBody()?.string()}")
                }
            } else {
                println("Image file not found: $path")
            }
        }

        // 3. 녹음 파일 업로드
        if (profile.voicePath.isNotEmpty()) {
            val file = File(profile.voicePath)
            if (file.exists()) {
                val requestFile = file.asRequestBody("audio/*".toMediaTypeOrNull())
                // "voice"는 서버에서 파일을 받을 MultipartForm-Data의 필드명입니다.
                val voicePart = MultipartBody.Part.createFormData("voice", file.name, requestFile)
                // "userId"는 서버에서 추가 데이터를 받을 MultipartForm-Data의 필드명입니다.
                val userIdPart = userId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                val voiceUploadResponse = userSignUpApi.uploadVoiceSample(voicePart, userIdPart)
                if (!voiceUploadResponse.isSuccessful) {
                    // 음성 파일 업로드 실패 처리
                    println("Failed to upload voice sample: Error: ${voiceUploadResponse.errorBody()?.string()}")
                }
            } else {
                println("Voice sample file not found: ${profile.voicePath}")
            }
        }
    }

}