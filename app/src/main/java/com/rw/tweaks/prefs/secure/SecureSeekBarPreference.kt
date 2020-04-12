package com.rw.tweaks.prefs.secure

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.edit
import com.rw.tweaks.R
import com.rw.tweaks.prefs.secure.base.BaseSecurePreference
import com.rw.tweaks.util.prefManager
import com.rw.tweaks.util.writeSetting

class SecureSeekBarPreference(context: Context, attrs: AttributeSet) : BaseSecurePreference(context, attrs) {
    var minValue: Int = 0
    var maxValue: Int = 100
    var defaultValue = 0
    var scale = 1.0f
    var units: String? = null

    init {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.SecureSeekBarPreference, 0, 0)
        val android = context.theme.obtainStyledAttributes(attrs, R.styleable.Preference, 0, 0)

        minValue = array.getInt(R.styleable.SecureSeekBarPreference_minValue, minValue)
        maxValue = array.getInt(R.styleable.SecureSeekBarPreference_maxValue, maxValue)
        defaultValue = android.getInt(R.styleable.Preference_android_defaultValue, defaultValue)
        scale = array.getFloat(R.styleable.SecureSeekBarPreference_scale, scale)
        units = array.getString(R.styleable.SecureSeekBarPreference_units)

        array.recycle()
        android.recycle()
    }

    override fun onValueChanged(newValue: Any?, key: String) {
        sharedPreferences.edit {
            val value = newValue?.toString()?.toFloat()
            if (value != null) {
                putFloat(writeKey!!, value)
            } else {
                remove(writeKey!!)
            }
        }

        context.writeSetting(type, writeKey, newValue?.toString())
    }
}