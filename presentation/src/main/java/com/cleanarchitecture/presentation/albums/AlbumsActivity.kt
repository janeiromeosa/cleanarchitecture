package com.cleanarchitecture.presentation.albums

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cleanarchitecture.news_sample_app.R
import com.cleanarchitecture.presentation.common.ErrorViewType
import com.cleanarchitecture.presentation.common.UiError
import com.cleanarchitecture.presentation.navigation.AppNavigator
import kotlinx.android.synthetic.main.activity_albums.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class AlbumsActivity : AppCompatActivity() {

    private val albumsViewModel: AlbumsViewModel by viewModel()
    private val navigator: AppNavigator by inject { parametersOf(this) }
    private val onItemClick: ((UiAlbum?) -> Unit) = {
        it?.let { album ->
            navigator.toAlbumDetails(album)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        initialiseView()
    }

    override fun onStart() {
        super.onStart()
        albumsViewModel.getAlbums()
        albumsViewModel.loadingLiveData.observe(this, Observer {
            loading(it)
        })
        albumsViewModel.contentLiveData.observe(this, Observer {
            content(it)
        })
        albumsViewModel.errorLiveData.observe(this, Observer {
            error(it)
        })
    }


    private fun initialiseView() {
        albumsAdapter = AlbumsAdapter(onItemClick)
        rv_albums.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_albums.adapter = albumsAdapter
    }

    private fun loading(isLoading: Boolean) {

    }

    private fun content(it: List<UiAlbum>) {
        it.let { response ->
            albumsAdapter.updateList(response)
        }
    }

    private fun error(error: UiError) {
        when (error.errorViewType) {
            ErrorViewType.DIALOG -> {
                AlertDialog.Builder(this)
                        .setTitle(error.title)
                        .setMessage(error.message)
                        .setPositiveButton(error.positive) { dialog, _ ->
                            dialog.dismiss()
                            albumsViewModel.getAlbums()
                        }
                        .setCancelable(error.cancelable)
                        .show()
            }
            else -> {
                // NOOP
            }
        }
    }
}
