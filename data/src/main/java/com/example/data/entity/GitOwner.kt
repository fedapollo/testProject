package com.example.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitOwner(
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val userAvatar: String
) : Parcelable
