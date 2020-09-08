package com.msr.msgallery.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.msr.msgallery.R
import com.msr.msgallery.data.model.Album
import com.msr.msgallery.data.repository.AlbumRepository
import com.msr.msgallery.databinding.ActivityMainBinding
import com.msr.msgallery.utils.SortBy
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.widget.AdapterView.OnItemSelectedListener as OnItemSelectedListener1

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var albumRepository: AlbumRepository
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainViewModel
        registerWithListeners()
        createSpinnerAdapter()
        mainViewModel.getAlbumsList().observe(this, {
            if (it.isNotEmpty()) {
                createAlbumAdapter(it)
            }
        })
        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                mainViewModel.getSortedList(text)
                return true
            }
        })
    }

    private fun createSpinnerAdapter() {
        val adapter = SortingSpinnerAdapter(
            this@MainActivity,
            R.layout.spinner_item,
            albumRepository.getSortingSpinnerItems()
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerSort.adapter = adapter
        spinnerSort.setSelection(0)
    }

    private fun createAlbumAdapter(list: MutableList<Album>) {
        binding.albumsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = AlbumsRecyclerViewAdapter(this@MainActivity,list)
        }
    }

    private fun registerWithListeners() {
        spinnerSort.onItemSelectedListener = (object : OnItemSelectedListener1 {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                p3: Long
            ) {
                mainViewModel.sortOrder = when (position) {
                    0 -> SortBy.RELEASE_DATE
                    1 -> SortBy.COLLECTION_NAME
                    2 -> SortBy.TRACK_NAME
                    3 -> SortBy.ARTIST_NAME
                    4 -> SortBy.COLLECTION_PRICE
                    else -> SortBy.RELEASE_DATE
                }
                mainViewModel.getSortedList(binding.searchView.query?.toString() ?: "")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        })
    }
}