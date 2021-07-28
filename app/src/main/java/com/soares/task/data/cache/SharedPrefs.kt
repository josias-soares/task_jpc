package com.soares.task.data.cache

import android.content.Context
import android.content.SharedPreferences


class SharedPrefs(context: Context) {

    private val mPreferences: SharedPreferences =
        context.getSharedPreferences("task_shared", Context.MODE_PRIVATE)

    fun putFloat(key: String, value: Float?) {
        mPreferences.edit().putFloat(key, value ?: 0f).apply()
    }

    fun putLong(key: String, value: Long?) {
        mPreferences.edit().putLong(key, value ?: 0L).apply()
    }

    fun putInt(key: String, value: Int) {
        mPreferences.edit().putInt(key, value).apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        mPreferences.edit().putBoolean(key, value).apply()
    }

    fun putString(key: String, value: String?) {
        if (value.isNullOrEmpty()) {
            return
        }

        mPreferences.edit().putString(key, value).apply()
    }

    fun remove(key: String) {
        mPreferences.edit().remove(key).apply()
    }

    fun getString(key: String): String {
        return mPreferences.getString(key, "") ?: ""
    }

    fun getBoolean(key: String): Boolean {
        return mPreferences.getBoolean(key, false)
    }

    fun getInt(key: String): Int {
        return mPreferences.getInt(key, 0)
    }

    fun getLong(key: String): Long {
        return mPreferences.getLong(key, 0L)
    }

    fun contains(key: String): Boolean {
        return mPreferences.contains(key)
    }

}
