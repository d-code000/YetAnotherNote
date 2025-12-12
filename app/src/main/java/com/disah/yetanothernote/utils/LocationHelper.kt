package com.disah.yetanothernote.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await

class LocationHelper(context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    sealed class LocationResult {
        data class Success(val latitude: Double, val longitude: Double) : LocationResult()
        data class Error(val message: String) : LocationResult()
    }

    suspend fun getCurrentLocation(context: Context): LocationResult {
        return try {
            // Проверка разрешений
            if (!hasLocationPermission(context)) {
                return LocationResult.Error("Нет разрешений на доступ к геолокации")
            }

            // Получение последней известной локации или текущей
            val location = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).await()

            if (location != null) {
                LocationResult.Success(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            } else {
                // Попытка получить последнюю известную локацию
                val lastLocation = fusedLocationClient.lastLocation.await()
                if (lastLocation != null) {
                    LocationResult.Success(
                        latitude = lastLocation.latitude,
                        longitude = lastLocation.longitude
                    )
                } else {
                    LocationResult.Error("Не удалось получить координаты. Включите GPS.")
                }
            }
        } catch (e: SecurityException) {
            LocationResult.Error("Нет разрешений на доступ к геолокации")
        } catch (e: Exception) {
            LocationResult.Error("Ошибка получения локации: ${e.message}")
        }
    }

    private fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }
}
