package com.likelion.remote.api



import com.likelion.remote.model.request.UserProfileRequest
import com.likelion.remote.model.response.ImageResponseDto
import com.likelion.remote.model.response.UserProfileResponseDto
import com.likelion.remote.model.response.VoiceSampleResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserSignUpApi {

    // POST /api/profiles
    // 사용자 프로필 정보를 등록합니다.
    @POST("api/profiles")
    suspend fun registerUserProfile(@Body request: UserProfileRequest): Response<UserProfileResponseDto>

    // POST /api/images/upload
    // 프로필 이미지 파일을 업로드합니다.
    // @Part("userId") userId: RequestBody -> userId는 보통 인증을 통해 서버에서 추출하거나,
    //                  multipart 요청의 form-data로 직접 전달하는 경우 사용합니다.
    //                  서버의 ImageRequestDto가 userId만 가지고 있기 때문에, 실제 파일과 함께 userId를 보내는 형태로 구성했습니다.
    @Multipart
    @POST("api/images/upload")
    suspend fun uploadProfileImage(
        @Part image: MultipartBody.Part, // 실제 이미지 파일
        @Part("userId") userId: RequestBody // 사용자 ID (String을 RequestBody로 변환)
    ): Response<ImageResponseDto> // 단일 이미지 업로드이므로 ImageResponse를 반환합니다.

    // POST /api/voice-samples/upload
    // 음성 샘플 파일을 업로드합니다.
    @Multipart
    @POST("api/voice-samples/upload")
    suspend fun uploadVoiceSample(
        @Part voice: MultipartBody.Part, // 실제 음성 파일
        @Part("userId") userId: RequestBody // 사용자 ID
    ): Response<VoiceSampleResponseDto>
}
