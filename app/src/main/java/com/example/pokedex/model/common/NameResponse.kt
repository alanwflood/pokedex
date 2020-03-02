package com.example.pokedex.model.common

import com.google.gson.annotations.SerializedName

data class NameResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
