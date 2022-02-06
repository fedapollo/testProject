package com.example.data.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.SerializedName

@JsonIgnoreProperties(ignoreUnknown = true)
data class GitRepositoriesResponseModel(
    @SerializedName("items") val repositories: List<GitRepository>
)
