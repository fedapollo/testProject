package com.example.data.entity

import com.google.gson.annotations.SerializedName

data class GitError(
    @SerializedName("message") val message: String
)