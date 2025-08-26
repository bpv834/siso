package com.likelion.data.login.repository

import UserProfileKeys.AGE
import UserProfileKeys.GENDER
import UserProfileKeys.INTRODUCE
import UserProfileKeys.NICKNAME
import UserProfileKeys.PHOTO_PATHS
import UserProfileKeys.PREFERENCE_SEX
import UserProfileKeys.VOICE_PATH
import android.content.Context
import android.graphics.Bitmap
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.likelion.domain.login.model.UserSignUpProfile
import com.likelion.domain.login.repository.UserSignUpRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject

class UserSignUpRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @ApplicationContext private val context: Context // 파일 저장을 위해 Context 주입
) : UserSignUpRepository {

     // DataStore에서 임시 저장된 사용자 프로필을 Flow 형태로 가져옵니다.
     // 데이터 변경 시 자동으로 새 값을 내보냅니다.
    override fun getTemporaryUserProfile(): Flow<UserSignUpProfile> = dataStore.data.map { preferences ->
        UserSignUpProfile(
            nickname = preferences[NICKNAME] ?: "",
            age = preferences[AGE] ?: 0,
            gender = preferences[GENDER] ?: "",
            preferenceSex = preferences[PREFERENCE_SEX] ?: "상관없음",
            photoPaths = preferences[PHOTO_PATHS]?.toList() ?: emptyList(),
            introduce = preferences[INTRODUCE] ?: "",
            voicePath = preferences[VOICE_PATH] ?: ""
        )
    }


    // 업데이트된 사용자 프로필을 DataStore에 임시 저장합니다.
    // 이전 데이터는 유지하며, 변경된 필드만 덮어씁니다.
        override suspend fun saveTemporaryUserProfile(profile: UserSignUpProfile) {
            dataStore.edit { preferences ->
                preferences[NICKNAME] = profile.nickname
                preferences[AGE] = profile.age
                preferences[GENDER] = profile.gender
                preferences[PREFERENCE_SEX] = profile.preferenceSex
                preferences[PHOTO_PATHS] = profile.photoPaths.toSet() // Set<String>으로 저장
                preferences[INTRODUCE] = profile.introduce
                preferences[VOICE_PATH] = profile.voicePath
            }
        }

  /*
     * DataStore에 임시 저장된 모든 프로필 데이터를 삭제합니다.
     * 회원가입 취소 시 유용합니다.*/
    override suspend fun clearTemporaryUserProfile() {
        dataStore.edit { it.clear() }
    }

    /**
     * 비트맵 리스트를 PNG 파일로 저장하고, 저장된 파일들의 경로를 반환합니다.
     */
    suspend fun savePhotosAndGetPaths(bitmaps: List<Bitmap>): List<String> {
        return bitmaps.mapNotNull { bitmap ->
            try {
                // 앱의 캐시 디렉터리에 PNG 파일로 저장
                val file = File(context.cacheDir, "${UUID.randomUUID()}.png")
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                }
                file.absolutePath // 파일의 절대 경로 반환
            } catch (e: Exception) {
                // 파일 저장 실패 시 에러 로깅
                e.printStackTrace()
                null
            }
        }
    }


/*     * 녹음된 오디오 데이터를 .m4a 파일로 저장하고, 저장된 파일의 경로를 반환합니다.
     * 이 메서드는 실제 녹음 로직이 완료된 후, 결과 파일 경로를 받아 처리합니다.
     * (실제 녹음 로직은 AudioRecord 등 별도의 컴포넌트에서 이루어짐)*/

    suspend fun saveVoiceAndGetPath(audioFile: File): String? {
        // audioFile은 이미 녹음 완료된 .m4a 파일이라고 가정
        // 이 파일을 캐시 디렉토리로 옮기거나, 이미 저장된 경로를 반환합니다.
        return try {
            val destinationFile = File(context.cacheDir, "${UUID.randomUUID()}.m4a")
            audioFile.copyTo(destinationFile, overwrite = true)
            destinationFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    // 회원가입 완료 후 서버에 전송하는 로직 (이번 구현 범위 외)
    override suspend fun registerProfileToServer(profile: UserSignUpProfile) {
        // TODO: 서버 API 호출 로직 구현 (이 가이드에서는 생략)
        println("회원가입 프로필 서버 전송 준비 완료: $profile")
    }
}