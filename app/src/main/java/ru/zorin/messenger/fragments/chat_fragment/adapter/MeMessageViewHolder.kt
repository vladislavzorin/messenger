package ru.zorin.messenger.fragments.chat_fragment.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.zorin.messenger.R
import ru.zorin.messenger.model.Message

class MeMessageViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    private val text = view.findViewById(R.id.text_content) as TextView
    private val time = view.findViewById(R.id.time_content) as TextView

    fun bind(message: Message, position: Int){
        text.text = "${message.messege}"
        time.text = "${message.time.substring(5..10)}"
    }

}