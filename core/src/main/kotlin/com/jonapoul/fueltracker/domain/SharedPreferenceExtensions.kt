package com.jonapoul.fueltracker.domain

import android.content.SharedPreferences
import androidx.core.content.edit
import com.jonapoul.common.core.PrefPair

inline fun <reified T> SharedPreferences.putIfNotNull(pref: PrefPair<T>, value: T?) {
    edit {
        when (value) {
            null -> return
            is String -> putString(pref.key, value)
            is Int -> putInt(pref.key, value)
            is Long -> putLong(pref.key, value)
            is Boolean -> putBoolean(pref.key, value)
            else -> error("Unexpected type: ${T::class.java.simpleName}")
        }
    }
}
