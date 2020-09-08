package com.msr.msgallery.data.repository

import android.content.Context
import com.google.gson.Gson
import com.msr.msgallery.data.constants.Constants
import com.msr.msgallery.data.model.Album
import com.msr.msgallery.data.model.AlbumsResponse
import javax.inject.Inject

class AlbumRepository @Inject constructor(private val context: Context) {

    fun getUniqueAlbums(): MutableList<Album>? {
        val distinctList = removeDuplicates()
        val resultList = mutableListOf<Album>()
        distinctList?.let { list ->
            for (item in list) {
                resultList.add(item)
            }
        }
        return resultList
    }



    private fun removeDuplicates(): MutableList<Album>? {
        getAlbumsList()?.let {
            it.let { list ->
                return list.distinctBy { item -> item.trackName } as MutableList<Album>?
            }
        }
        return null
    }

    private fun getAlbumsList(): List<Album>? {
        val responseString: String? =
            context.assets.open(Constants.ALBUMS_RESPONSE).bufferedReader().run {
                readText()
            }
        val albumsResponse = Gson().fromJson(responseString, AlbumsResponse::class.java)
        return albumsResponse.albums
    }

    fun getSortingSpinnerItems(): List<String> {
        return arrayListOf(
            "Release date",
            "Collection",
            "Track",
            "Artist",
            "Collection Price - Descending"
        )
    }
}