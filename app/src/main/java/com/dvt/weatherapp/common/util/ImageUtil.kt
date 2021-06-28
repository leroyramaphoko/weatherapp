package com.dvt.weatherapp.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.dvt.weatherapp.R
import com.dvt.weatherapp.common.enums.WeatherCondition
import com.dvt.weatherapp.common.enums.WeatherCondition.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class ImageUtil {
    companion object {
        fun getForecastWeatherIcon(weatherId: Int): Int? {
            val weatherCondition = WeatherCondition.from(weatherId) ?: return null

            return when (weatherCondition) {
                THUNDERSTORM, DRIZZLE, RAIN, SNOW -> R.drawable.rain
                ATMOSPHERE, CLOUDS -> R.drawable.partlysunny
                CLEAR -> R.drawable.clear
            }
        }

        fun bitmapDescriptorFromVector(
            context: Context,
            @DrawableRes vectorResId: Int
        ): BitmapDescriptor? {
            val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
            vectorDrawable!!.setBounds(
                0,
                0,
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight
            )
            val bitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            vectorDrawable.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}