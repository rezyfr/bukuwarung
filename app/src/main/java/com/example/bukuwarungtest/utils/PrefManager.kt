package com.example.bukuwarungtest.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences


class PrefManager private constructor() {

    companion object {

        const val PREF_CURRENT_USER_EMAIL = "pref_current_user_email"
        const val PREF_CURRENT_USER_PHONE = "pref_current_user_phone"
        const val PREF_CURRENT_NAME = "pref_current_name"
        const val PREF_CURRENT_PIC_PATH = "pref_current_pic_url"

        @Volatile
        private var preference: SharedPreferences? = null

        @Volatile
        private var instance: PrefManager? = null

        @Volatile
        private var userManager: UserManager? = null

        @SuppressLint("HardwareIds")
        @Synchronized
        fun init(context: Context) {
            if (instance == null) {
                instance = PrefManager()
            }
            if (preference == null) {
                val preferenceName = context.applicationContext.packageName + ".PREFERENCE_MANAGER"
                preference = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
            }
            // initialize managers
            if (userManager == null) {
                userManager = instance?.UserManager()
            }
        }

        /**
         * Get PrefManager instance
         */
        fun getInstance(): PrefManager {
            return instance ?: synchronized(this) {
                instance ?: PrefManager()
                    .also {
                        instance = it
                    }
            }
        }
    }

    /**
     * Get UserManager instance
     */
    fun getUserManager(): UserManager {
        return userManager ?: synchronized(this) {
            userManager ?: getInstance().UserManager()
                .also {
                    userManager = it
                }
        }
    }

    @Synchronized
    fun clear() {
        preference?.edit()?.clear()?.apply()
    }

    inner class UserManager {

        @Synchronized
        fun clear() {
            preference?.edit()?.apply {
                remove(PREF_CURRENT_USER_EMAIL)
                remove(PREF_CURRENT_USER_PHONE)
                remove(PREF_CURRENT_NAME)
                remove(PREF_CURRENT_PIC_PATH)
            }?.apply()
        }

        @Synchronized
        fun saveCurrentUserEmail(userEmail: String?) {
            preference?.edit()?.apply {
                putString(PREF_CURRENT_USER_EMAIL, userEmail)
            }?.apply()
        }

        @Synchronized
        fun saveCurrentUserPhone(phone: String?) {
            preference?.edit()?.apply {
                putString(PREF_CURRENT_USER_PHONE, phone)
            }?.apply()
        }

        @Synchronized
        fun saveCurrentName(name: String?) {
            preference?.edit()?.apply {
                putString(PREF_CURRENT_NAME, name)
            }?.apply()
        }

        @Synchronized
        fun saveCurrentPicPath(path: String?) {
            preference?.edit()?.apply {
                putString(PREF_CURRENT_PIC_PATH, path)
            }?.apply()
        }

        @Synchronized
        fun getCurrentName(): String {
            return preference?.getString(PREF_CURRENT_NAME, "").orEmpty()
        }

        @Synchronized
        fun getCurrentPhone(): String {
            return preference?.getString(PREF_CURRENT_USER_PHONE, "").orEmpty()
        }

        @Synchronized
        fun getCurrentEmail(): String {
            return preference?.getString(PREF_CURRENT_USER_EMAIL, "").orEmpty()
        }

        @Synchronized
        fun getCurrentPicPath(): String {
            return preference?.getString(PREF_CURRENT_PIC_PATH, "").orEmpty()
        }
    }
}