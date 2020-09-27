package ru.zorin.messenger.fragments.chat_fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.zorin.messenger.R
import ru.zorin.messenger.model.Message

class MessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listItems: MutableList<Message>  = ArrayList()
    var userId:Int = 0

    fun setData(listItems: MutableList<Message>){
        this.listItems = listItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
       if (viewType == 0){
           return MeMessageViewHolder(layoutInflater.inflate(R.layout.item_chat_facebook_me, parent, false))
       }else{
           return YouMessageViewHolder(layoutInflater.inflate(R.layout.item_chat_facebook_you, parent, false))
       }

    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is YouMessageViewHolder){
            holder.bind(listItems[position],position)
        }else{
            (holder as MeMessageViewHolder).bind(listItems[position],position)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (listItems[position].userId == userId) 0 else 1
    }
}