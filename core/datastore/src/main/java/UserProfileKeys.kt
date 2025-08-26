import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

object UserProfileKeys {
    val NICKNAME = stringPreferencesKey("nickname")
    val AGE = intPreferencesKey("age")
    val GENDER = stringPreferencesKey("gender")
    val PREFERENCE_SEX = stringPreferencesKey("preference_sex")
    val PHOTO_PATHS = stringSetPreferencesKey("photo_paths")
    val INTRODUCE = stringPreferencesKey("introduce")
    val VOICE_PATH = stringPreferencesKey("voice_path")
}