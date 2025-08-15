package com.likelion.data.model

import com.google.gson.annotations.SerializedName

data class AdministratorEntity(
    @SerializedName("id")
    val id: Long  // BIGINT, PRIMARY KEY, Auto Increment
)