package com.happytek.mercaplus.interfaces

import android.content.Intent
import android.view.View

interface RecyclerViewClickListener {

    fun recyclerViewListClicked(value : Int)

    fun getIntent(value : Intent)
}