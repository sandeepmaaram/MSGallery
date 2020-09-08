package com.msr.msgallery.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msr.msgallery.data.model.Album
import com.msr.msgallery.data.repository.AlbumRepository
import com.msr.msgallery.utils.SortBy
import java.util.*

class MainViewModel @ViewModelInject constructor(private val albumRepository: AlbumRepository) :
    ViewModel() {
    private var albumsOriginalList: MutableList<Album>? = mutableListOf()
    private var albumsList: MutableLiveData<MutableList<Album>> = MutableLiveData()
    var sortOrder: SortBy = SortBy.RELEASE_DATE


    fun getAlbumsList(): MutableLiveData<MutableList<Album>> {
        albumsOriginalList = albumRepository.getUniqueAlbums()
        getSortedList(searchHint = null)
        return albumsList
    }

    /**
     * Method to get sorted list
     */
    fun getSortedList(searchHint: String?) {
        albumsList.value = when (sortOrder) {
            SortBy.RELEASE_DATE -> getListInReleaseDateOrder(searchHint)
            SortBy.COLLECTION_NAME -> getListInCollectionOrder(searchHint)
            SortBy.TRACK_NAME -> getListInTrackOrder(searchHint)
            SortBy.ARTIST_NAME -> getListInArtistOrder(searchHint)
            SortBy.COLLECTION_PRICE -> getListInCollectionPrice(searchHint)
        }
    }

    /**
     * Method to search list
     */
    private fun getSearchResult(searchHint: String): MutableList<Album>? {
        return albumsOriginalList?.filter {
            it.trackName?.toLowerCase(Locale.getDefault())
                ?.contains(searchHint.toLowerCase(Locale.getDefault()))?:false ||
                    it.artistName.toLowerCase(Locale.getDefault()).contains(
                        searchHint.toLowerCase(
                            Locale.getDefault()
                        )
                    ) ||
                    it.collectionName?.toLowerCase(Locale.getDefault())
                        ?.contains(searchHint.toLowerCase(Locale.getDefault())) ?: false
        } as MutableList<Album>?
    }

    /**
     * Method to sort list in order of date
     */
    private fun getListInReleaseDateOrder(searchHint: String?): MutableList<Album>? {
        val list: MutableList<Album>? = if (searchHint != null && searchHint.isNotEmpty())
            getSearchResult(searchHint)
        else albumsOriginalList
        return list?.sortedBy { it.releaseDate } as MutableList<Album>?
    }

    /**
     * Method to sort list in order of collection name
     */
    private fun getListInCollectionOrder(searchHint: String?): MutableList<Album>? {
        val list: MutableList<Album>? = if (searchHint != null && searchHint.isNotEmpty())
            getSearchResult(searchHint)
        else albumsOriginalList
        return list?.sortedBy { it.collectionName } as MutableList<Album>?
    }

    /**
     * Method to sort list in order of track name
     */
    private fun getListInTrackOrder(searchHint: String?): MutableList<Album>? {
        val list: MutableList<Album>? = if (searchHint != null && searchHint.isNotEmpty())
            getSearchResult(searchHint)
        else albumsOriginalList
        return list?.sortedBy { it.trackName } as MutableList<Album>?
    }

    /**
     * Method to sort list in order of artist name
     */
    private fun getListInArtistOrder(searchHint: String?): MutableList<Album>? {
        val list: MutableList<Album>? = if (searchHint != null && searchHint.isNotEmpty())
            getSearchResult(searchHint)
        else albumsOriginalList
        return list?.sortedBy { it.artistName } as MutableList<Album>?
    }

    /**
     * Method to sort list in order of price
     */
    private fun getListInCollectionPrice(searchHint: String?): MutableList<Album>? {
        val list: MutableList<Album>? = if (searchHint != null && searchHint.isNotEmpty())
            getSearchResult(searchHint)
        else albumsOriginalList
        list?.sortByDescending { it.collectionPrice ?: 0.0 }
        return list
    }
}