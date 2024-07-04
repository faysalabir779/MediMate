package com.example.medimate.pref

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val USER_ID = stringPreferencesKey("user_id")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_PASSWORD = stringPreferencesKey("user_password")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val USER_ADDRESS = stringPreferencesKey("user_address")
    val USER_PHONE = stringPreferencesKey("user_phone")
    val USER_PINCODE = stringPreferencesKey("user_pinCode")
}