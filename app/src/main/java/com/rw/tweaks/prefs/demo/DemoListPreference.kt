package com.rw.tweaks.prefs.demo

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.TextUtils
import android.util.AttributeSet
import androidx.core.content.res.TypedArrayUtils
import androidx.preference.DialogPreference
import com.rw.tweaks.R
import com.rw.tweaks.prefs.demo.base.BaseDemoPreference
import com.rw.tweaks.prefs.secure.base.BaseSecurePreference
import com.rw.tweaks.util.*
import com.rw.tweaks.util.verifiers.BaseListPreferenceVerifier

@SuppressLint("RestrictedApi")
class DemoListPreference(context: Context, attrs: AttributeSet) : BaseDemoPreference(context, attrs), IListPreference {
    private var setValue: Boolean = false
    override var value: String? = null
        set(value) {
            // Always persist/notify the first time.
            val changed = !TextUtils.equals(field, value)
            if (changed || !setValue) {
                field = value
                setValue = true
                persistString(value)
                if (changed) {
                    notifyChanged()
                }
            }
        }

    override var entries: Array<CharSequence?>? = null
    override var entryValues: Array<CharSequence?>? = null

    init {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.SecureListPreference, 0, 0)

        entries = TypedArrayUtils.getTextArray(
            array, androidx.preference.R.styleable.ListPreference_entries,
            androidx.preference.R.styleable.ListPreference_android_entries
        )

        entryValues = TypedArrayUtils.getTextArray(
            array, androidx.preference.R.styleable.ListPreference_entryValues,
            androidx.preference.R.styleable.ListPreference_android_entryValues
        )

        array.recycle()
    }

    override fun onSetInitialValue(defaultValue: Any?) {
        value = preferenceManager.sharedPreferences.getString(key, null) ?: defaultValue?.toString() ?: entryValues!![0].toString()
        summary = entries?.get(findIndexOfValue(value))
    }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any? {
        return a.getString(index)
    }

    override fun onValueChanged(newValue: Any?, key: String) {
        summary = entries?.get(findIndexOfValue(newValue?.toString()))
        super.onValueChanged(newValue, key)
    }
}