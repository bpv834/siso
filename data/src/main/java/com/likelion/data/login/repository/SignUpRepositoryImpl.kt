package com.likelion.data.login.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.likelion.data.login.mapper.toUserProfileRequest
import com.likelion.domain.login.model.UserSignUpProfile
import com.likelion.domain.login.repository.SignUpRepository
import com.likelion.remote.api.UserApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import java.io.IOException
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val userId : Long
   // private val voiceApi: UserSignUpApi,
    ) : SignUpRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun addProfile(
        refreshToken: String,
        profile: UserSignUpProfile
    ) {
        try {
            // 1. 도메인 모델 → DTO 변환
            val userProfileRequest = profile.toUserProfileRequest()
            Timber.d("userRequest : $userProfileRequest")

            // 2. API 호출
            val response = userApiService.registerUserProfile(
                refreshToken = "Bearer $refreshToken",
                request = userProfileRequest
            )

            // 3. 실패 응답 처리
            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string() ?: "알 수 없는 오류"
                throw Exception("프로필 등록 실패: $errorBody")
            }

            // 4. 성공 응답 처리
            val registeredUserProfile = response.body()
            Timber.d("등록 완료된 UserProfile : $registeredUserProfile")

        } catch (e: IOException) {
            // 네트워크 문제 (인터넷 끊김 등)
            Timber.e(e, "네트워크 오류 발생")
            throw Exception("네트워크 오류: ${e.localizedMessage}")
        } catch (e: HttpException) {
            // HTTP 프로토콜 에러
            Timber.e(e, "HTTP 오류 발생")
            throw Exception("서버 오류: ${e}")
        } catch (e: Exception) {
            // 그 외 예외
            Timber.e(e, "예상치 못한 오류 발생")
            throw Exception("예기치 못한 오류: ${e.localizedMessage}")
        }
    }

    override suspend fun addImage(
        imgPathList: List<String>,
        refreshToken: String,
    ) {
        // 이미지 경로 리스트를 순회하며 각 이미지 업로드
        imgPathList.forEach { path ->
            try {
                // 1. 경로로부터 파일 객체 생성
                val file = File(path)

                // 2. 파일을 API 요청을 위한 MultipartBody.Part로 변환
                // "image"는 API에서 요구하는 파라미터 이름입니다.
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

                // 3. 변환된 파일을 사용하여 API 호출
                val response = userApiService.uploadProfileImage(
                    refreshToken = refreshToken,
                    image = imagePart,
                    userId = userId
                )

                // API 응답 처리 (예: 성공 여부 확인)
                if (response.isSuccessful) {
                    // 업로드 성공
                    println("Image upload successful: ${file.name}")
                } else {
                    // 업로드 실패
                    println("Image upload failed: ${file.name}, Code: ${response.code()}")
                }
            } catch (e: Exception) {
                // 파일이 없거나 업로드 중 오류 발생 시 예외 처리
                println("Error uploading image from path: $path, Error: ${e.message}")
            }
        }
    }

    override suspend fun addVoice(voicePath: String,refreshToken: String) {
        TODO("Not yet implemented")
    }

}