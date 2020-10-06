package ru.zorin.messenger.fragments.users_fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.zorin.messenger.R
import ru.zorin.messenger.fragments.users_fragment.UserListener
import ru.zorin.messenger.model.User

class UserAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listItems: MutableList<User>  = ArrayList()
    lateinit var listener: UserListener


    fun setData(listItems: MutableList<User>){
        listItems.sortByDescending { it.lastMessage.time }
        this.listItems = listItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(layoutInflater.inflate(R.layout.item_user, parent, false),listener)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UserViewHolder).bind(listItems[position])
    }
}