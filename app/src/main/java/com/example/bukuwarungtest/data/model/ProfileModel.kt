package com.example.bukuwarungtest.data.model

import android.os.Parcelable
import androidx.room.PrimaryKey

data class ProfileModel(
    var name: String? = "",
    var phone: String? = "",
    var email: String? = "",
    var img_path: String? = ""
)