package com.cleanarchitecture.presentation.RecentlyVieweds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cleanarchitecture.news_sample_app.R
import com.cleanarchitecture.presentation.albums.UiRecentlyViewed
import kotlinx.android.synthetic.main.recently_viewed_details.view.*

class RecentlyViewedAdapter(val onItemClick: (UiRecentlyViewed?) -> Unit) : RecyclerView.Adapter<RecentlyViewedAdapter.RecentlyViewedViewHolder>() {

    var RecentlyVieweds = mutableListOf<UiRecentlyViewed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecentlyViewedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recently_viewed_details, parent, false))

    override fun getItemCount() = RecentlyVieweds.size

    override fun onBindViewHolder(holder: RecentlyViewedViewHolder, position: Int) {
        holder.bind(RecentlyVieweds[position])
    }

    inner class RecentlyViewedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(uiRecentlyViewed: UiRecentlyViewed) {
            with(itemView) {
                img_recently_viewed.setImageURI(uiRecentlyViewed.description)
                setOnClickListener {
                    onItemClick.invoke(uiRecentlyViewed)
                }
            }
        }
    }

    fun updateList(list: List<UiRecentlyViewed>) {
        if (list.isNotEmpty()) {
            RecentlyVieweds.clear()
            RecentlyVieweds.addAll(list)
            notifyDataSetChanged()
        }
    }
}