package com.cleanarchitecture.presentation.albums

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiRecentlyViewed(
        var image: String? = null,
        var text: String? = null
) : Parcelable