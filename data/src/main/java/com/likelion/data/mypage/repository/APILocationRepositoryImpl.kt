package com.likelion.data.mypage.repository
import android.content.Context
import android.annotation.SuppressLint
import android.location.Geocoder
import android.util.Log.d
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.likelion.domain.mypage.LocationState
import com.likelion.domain.mypage.repository.APILocationRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.util.Locale
import javax.inject.Inject

class APILocationRepositoryImpl @Inject constructor(
    context: Context
): APILocationRepository {

    init {
        setContext(context)
    }

    private var fusedLocationClient : FusedLocationProviderClient? = null
    private var geocoder : Geocoder? = null
    fun setContext(context: Context){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        geocoder = Geocoder(context, Locale.getDefault())
    }

    @SuppressLint("MissingPermission")
    override fun getLocationFlow() : Flow<LocationState> = callbackFlow {
        trySend(LocationState.Loading) // ① 위치를 가져오기 전 로딩 상태 전달

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L)
            .setMinUpdateIntervalMillis(2000L)
            .build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation
                d("state","location $location")
                if (location != null) {
                    val address = geocoder!!
                        .getFromLocation(location.latitude, location.longitude, 1)
                        ?.firstOrNull()?.getAddressLine(0)
                    if (address != null) {
                        trySend(LocationState.Success(address)) // ② 위치 값 전달
                        d("state","data $address")
                        close() // Flow 종료 가능
                    } else {
                        trySend(LocationState.Error("주소를 가져올 수 없습니다"))
                    }
                }
            }

        }

        fusedLocationClient!!.requestLocationUpdates(locationRequest, callback, null)

        awaitClose {
            fusedLocationClient!!.removeLocationUpdates(callback)
        }

    }
}