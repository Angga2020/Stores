package com.example.stores.UI.Stores
import com.example.stores.Model.Stores.Data

interface onStoresItemClickListener{
    fun onItemClick(store: Data, position: Int)
}