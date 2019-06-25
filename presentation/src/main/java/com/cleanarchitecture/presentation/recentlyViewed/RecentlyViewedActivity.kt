package com.cleanarchitecture.presentation.RecentlyViewed

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
import com.cleanarchitecture.presentation.RecentlyVieweds.RecentlyViewedAdapter
import com.cleanarchitecture.presentation.albums.RecentlyViewedViewModel
import com.cleanarchitecture.presentation.albums.UiRecentlyViewed
import com.cleanarchitecture.presentation.common.ErrorViewType
import com.cleanarchitecture.presentation.common.UiError
import com.cleanarchitecture.presentation.navigation.AppNavigator
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class RecentlyViewedActivity : AppCompatActivity() {

    private val RecentlyViewedViewModel: RecentlyViewedViewModel by viewModel()
    private val navigator: AppNavigator by inject { parametersOf(this) }
    private val onItemClick: ((UiRecentlyViewed?) -> Unit) = {
        it?.let { RecentlyViewed ->
            navigator.toRecentlyViewedDetails(RecentlyViewed)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    private lateinit var recentlyViewedAdapter: RecentlyViewedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recently_viewed_recycler_view)
        initialiseView()
    }

    override fun onStart() {
        super.onStart()
        RecentlyViewedViewModel.getRecentlyViewed()
        RecentlyViewedViewModel.loadingLiveData.observe(this, Observer {
            loading(it)
        })
        RecentlyViewedViewModel.contentLiveData.observe(this, Observer {
            content(it)
        })
        RecentlyViewedViewModel.errorLiveData.observe(this, Observer {
            error(it)
        })
    }


    private fun initialiseView() {
        RecentlyViewedAdapter = RecentlyViewedAdapter(onItemClick)
        rv_RecentlyViewed.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_RecentlyViewed.adapter = RecentlyViewedAdapter
    }

    private fun loading(isLoading: Boolean) {

    }

    private fun content(it: List<UiRecentlyViewed>) {
        it.let { response ->
            RecentlyViewedAdapter.updateList(response)
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
                            RecentlyViewedViewModel.getRecentlyViewed()
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
