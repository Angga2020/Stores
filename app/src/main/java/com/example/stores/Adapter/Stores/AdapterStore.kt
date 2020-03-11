package com.example.stores.Adapter.Stores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.utils.GlideApp
import com.example.stores.Model.Stores.Data
import com.example.stores.R
import com.example.stores.UI.Stores.onStoresItemClickListener
import kotlinx.android.synthetic.main.store_list.view.*

class AdapterStore(val stores: ArrayList<Data>, var clickListner: onStoresItemClickListener) :
    RecyclerView.Adapter<AdapterStore.ViewHolder>() {

    override fun getItemCount(): Int = stores.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.store_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(stores.get(position),clickListner)
    }

    fun refreshAdapter(storeList: ArrayList<Data>) {
        this.stores.addAll(storeList)
        notifyItemRangeChanged(0, this.stores.size)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var view: View = itemView
        private lateinit var store: Data

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            Toast.makeText(view.context, "${store.attributes.name}", Toast.LENGTH_SHORT)
                .show()
        }

        fun bindData(store: Data, action: onStoresItemClickListener) {
            this.store = store
//            if (store.attributes.logoUrl) {
                GlideApp.with(view.context)
                    .load(store.attributes.logoUrl)
//                    .placeholder(R.drawable.noimage)
//                    .circleCrop()
                    .into(view.ivLogo)
            GlideApp.with(view.context)
                .load(store.attributes.coverUrl)
//                    .placeholder(R.drawable.noimage)
//                    .circleCrop()
                .into(view.ivCover)
//            }
//            else {
//                GlideApp.with(view.context)
//                    .load(R.drawable.noimage)
////                    .circleCrop()
//                    .into(view.ivProductImage)
//            }
            view.tvStoreName.setText(store.attributes.name)
            view.tvStoreTagLine.setText(store.attributes.tagline)
            itemView.setOnClickListener {
                action.onItemClick(store, adapterPosition)
            }
        }
    }
}