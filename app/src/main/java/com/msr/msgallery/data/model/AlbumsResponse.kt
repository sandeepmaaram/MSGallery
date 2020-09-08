package com.msr.msgallery.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AlbumsResponse(
    @SerializedName("resultCount") var resultCount: Int?,
    @SerializedName("results") var albums: List<Album>?
) : Serializable

data class Album(
    @SerializedName("artistName") var artistName: String,
    @SerializedName("collectionName") var collectionName: String?,
    @SerializedName("trackName") var trackName: String?,
    @SerializedName("collectionPrice") var collectionPrice: Double?,
    @SerializedName("releaseDate") var releaseDate: String?,
    @SerializedName("artworkUrl100") var artworkUrl100: String?,
    var selected: Boolean = false
) : Serializable