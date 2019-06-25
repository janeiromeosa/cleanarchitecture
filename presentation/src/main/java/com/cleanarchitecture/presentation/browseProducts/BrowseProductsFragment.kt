package com.cleanarchitecture.presentation.browseProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cleanarchitecture.news_sample_app.R
import com.cleanarchitecture.presentation.common.extensions.inflate
import com.cleanarchitecture.presentation.navigation.AppNavigator
import kotlinx.android.synthetic.main.fragment_browse_products.*
import kotlinx.android.synthetic.main.fragment_home.*

class BrowseProductsFragment : Fragment() {

    private lateinit var navigator: AppNavigator

    companion object {
        const val TAG = "BrowseProductsFragment"
        fun newInstance(navigator: AppNavigator) = BrowseProductsFragment().apply {
            this.navigator = navigator
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = container?.inflate(R.layout.fragment_browse_products)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_img_browse_products.setOnClickListener { navigator.toAlbums() }
    }
}