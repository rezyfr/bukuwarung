package com.example.bukuwarungtest.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

data class UserModel(
    var ad: Ad,
    var `data`: List<User>,
    var page: Int,
    var per_page: Int,
    var total: Int,
    var total_pages: Int
)

data class Ad(
    var company: String,
    var text: String,
    var url: String
)

@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    var id: Int? = 0,
    var first_name: String? = "",
    var last_name: String? = "",
    var email: String? = "",
    var avatar: String? = ""
): Parcelable